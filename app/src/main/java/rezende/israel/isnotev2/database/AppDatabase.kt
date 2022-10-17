package rezende.israel.isnotev2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rezende.israel.isnotev2.database.dao.NotaDao
import rezende.israel.isnotev2.database.migrations.MIGRATION_1_2
import rezende.israel.isnotev2.database.migrations.MIGRATION_2_3
import rezende.israel.isnotev2.model.Nota

@Database(
    version = 3,
    entities = [Nota::class],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notaDao(): NotaDao

    companion object {
        @Volatile
        private var db: AppDatabase? = null

        fun instancia(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "isnote.db"
            ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()
        }
    }
}