package alvin.ui.layout.listing

import alvin.ui.layout.listing.domain.models.FileItem
import alvin.ui.layout.listing.domain.models.FileType
import alvin.ui.layout.listing.views.ListViewActivity
import alvin.ui.layout.listing.views.RecyclerViewActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Qualifier

@Module
interface ListingModule {

    @ContributesAndroidInjector(modules = [CommonModule::class])
    fun listViewActivity(): ListViewActivity

    @ContributesAndroidInjector(modules = [CommonModule::class])
    fun recyclerViewActivity(): RecyclerViewActivity

    @Qualifier
    @Retention
    annotation class DataList

    @Module
    abstract class CommonModule {

        @Module
        companion object {

            @Provides
            @DataList
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
}
