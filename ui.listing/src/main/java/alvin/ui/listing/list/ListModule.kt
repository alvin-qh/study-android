package alvin.ui.listing.list

import alvin.ui.listing.list.listview.ListViewModule
import alvin.ui.listing.list.recyclerview.RecyclerViewModule
import dagger.Module

@Module(includes = [
    ListViewModule::class,
    RecyclerViewModule::class
])
interface ListModule
