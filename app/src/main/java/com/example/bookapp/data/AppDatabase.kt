package com.example.bookapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.json.JSONArray

@Database(
    entities = [
        FieldEntity::class,
        TaziehEntity::class,
        RoleEntity::class,
        SectionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fieldDao(): FieldDao
    abstract fun taziehDao(): TaziehDao
    abstract fun roleDao(): RoleDao
    abstract fun sectionDao(): SectionDao
    abstract fun searchDao(): SearchDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bookapp.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

/**
 * داده‌های واقعی/نمونه را از فایل assets/sample_data.json می‌خواند
 * و در صورتی که دیتابیس خالی باشد، آن‌ها را وارد می‌کند.
 * ساختار JSON باید خروجی اسکریپت scripts/docx_to_json.py باشد:
 * زمینه -> تعزیه -> نقش -> بخش (با متن)
 */
suspend fun seedDatabaseIfEmpty(context: Context, db: AppDatabase) {
    if (db.fieldDao().getAll().isNotEmpty()) return

    val jsonText = context.assets.open("sample_data.json")
        .bufferedReader(Charsets.UTF_8).use { it.readText() }
    val fields = JSONArray(jsonText)

    for (fi in 0 until fields.length()) {
        val fieldObj = fields.getJSONObject(fi)
        val fieldId = db.fieldDao().insert(FieldEntity(title = fieldObj.getString("title")))

        val taziehs = fieldObj.getJSONArray("taziehs")
        for (ti in 0 until taziehs.length()) {
            val taziehObj = taziehs.getJSONObject(ti)
            val taziehId = db.taziehDao().insert(
                TaziehEntity(fieldId = fieldId, title = taziehObj.getString("title"))
            )

            val roles = taziehObj.getJSONArray("roles")
            for (ri in 0 until roles.length()) {
                val roleObj = roles.getJSONObject(ri)
                val roleId = db.roleDao().insert(
                    RoleEntity(taziehId = taziehId, title = roleObj.getString("title"))
                )

                val sections = roleObj.getJSONArray("sections")
                for (si in 0 until sections.length()) {
                    val secObj = sections.getJSONObject(si)
                    db.sectionDao().insert(
                        SectionEntity(
                            roleId = roleId,
                            orderIndex = si,
                            title = secObj.getString("title"),
                            content = secObj.getString("content")
                        )
                    )
                }
            }
        }
    }
}
