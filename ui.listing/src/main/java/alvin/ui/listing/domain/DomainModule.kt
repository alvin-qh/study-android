package alvin.ui.listing.domain

import alvin.ui.listing.domain.models.FileItem
import alvin.ui.listing.domain.models.FileType
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
abstract class DomainModule {

    @Qualifier
    @Retention
    annotation class DataList

    @Module
    companion object {

        @Provides
        @DataList
        @Singleton
        @JvmStatic
        fun fileItems(): List<FileItem> {
            return listOf(
                    FileItem(1L, FileType.FILE, "README.md"),
                    FileItem(2L, FileType.DIRECTORY, "Project"),
                    FileItem(3L, FileType.FILE, "Main.cpp"),
                    FileItem(4L, FileType.FILE, "Main.hpp"),
                    FileItem(5L, FileType.FILE, "README.md"),
                    FileItem(6L, FileType.DIRECTORY, "Project"),
                    FileItem(7L, FileType.FILE, "Main.cpp"),
                    FileItem(8L, FileType.FILE, "Main.hpp"),
                    FileItem(9L, FileType.FILE, "README.md"),
                    FileItem(10L, FileType.DIRECTORY, "Project"),
                    FileItem(11L, FileType.FILE, "Main.cpp"),
                    FileItem(12L, FileType.FILE, "Main.hpp"),
                    FileItem(13L, FileType.FILE, "README.md"),
                    FileItem(14L, FileType.DIRECTORY, "Project"),
                    FileItem(15L, FileType.FILE, "Main.cpp"),
                    FileItem(16L, FileType.FILE, "Main.hpp")
            )
        }
    }
}
