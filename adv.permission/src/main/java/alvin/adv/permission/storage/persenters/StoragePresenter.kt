package alvin.adv.permission.storage.persenters

import alvin.adv.permission.storage.StorageContracts
import alvin.adv.permission.storage.models.Person
import alvin.base.kotlin.lib.common.rx.RxManager
import alvin.lib.common.utils.Storages
import alvin.lib.mvp.adapters.ViewPresenterAdapter
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Completable
import io.reactivex.Single
import java.io.*
import javax.inject.Inject

class StoragePresenter
@Inject constructor(
        view: StorageContracts.View,
        private val rxManager: RxManager,
        private val storages: Storages,
        private val objectMapper: ObjectMapper
) : ViewPresenterAdapter<StorageContracts.View>(view), StorageContracts.Presenter {

    companion object {
        val TAG = StoragePresenter::class.simpleName
    }

    override fun savePerson(person: Person) {
        rxManager.with(Completable.create {
            val json = objectMapper.writeValueAsString(person)
            val file = storages.createExternalStorageFile("persons", "person.data")

            BufferedOutputStream(FileOutputStream(file)).use {
                it.write(json.toByteArray())
            }
            it.onComplete()
        }).subscribe({
            withView { it.saveComplete() }
        }, { throwable ->
            Log.e(TAG, "Write file failed", throwable)
            withView { it.saveFailed() }
        })
    }

    override fun loadPerson() {
        rxManager.with(Single.create<Person> {
            val file = storages.createExternalStorageFile("persons", "person.data")

            val buffer = StringBuilder()
            BufferedReader(InputStreamReader(FileInputStream(file), Charsets.UTF_8)).use {
                val chars = CharArray(1024)
                while (true) {
                    val len = it.read(chars, 0, chars.size)
                    if (len <= 0) {
                        break
                    }
                    buffer.append(chars, 0, len)
                }
            }
            if (!buffer.isEmpty()) {
                val person = objectMapper.readValue(buffer.toString(), Person::class.java)
                if (person != null) {
                    it.onSuccess(person)
                }
            }
        }).subscribe({ person ->
            withView { it.showPerson(person) }
        }, { throwable ->
            Log.e(TAG, "Read file failed", throwable)
            withView { it.loadFailed() }
        })
    }

    override fun onDestroy() {
        rxManager.clear()
        super.onDestroy()
    }
}
