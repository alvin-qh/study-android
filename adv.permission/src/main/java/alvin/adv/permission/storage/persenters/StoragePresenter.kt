package alvin.adv.permission.storage.persenters

import alvin.adv.permission.storage.StorageContracts
import alvin.adv.permission.storage.models.Person
import alvin.lib.common.rx.RxDecorator
import alvin.lib.common.rx.RxType
import alvin.lib.common.utils.Storages
import alvin.lib.mvp.contracts.adapters.PresenterAdapter
import android.annotation.SuppressLint
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.Completable
import io.reactivex.Single
import java.io.*
import javax.inject.Inject

class StoragePresenter
@Inject constructor(
        view: StorageContracts.View,
        @RxType.IO private val rxDecoratorBuilder: RxDecorator.Builder,
        private val storages: Storages,
        private val objectMapper: ObjectMapper
) :
        PresenterAdapter<StorageContracts.View>(view),
        StorageContracts.Presenter {

    companion object {
        val TAG = StoragePresenter::class.simpleName
    }

    @SuppressLint("CheckResult")
    override fun savePerson(person: Person) {
        val decorator = rxDecoratorBuilder.build()
        decorator.de(Completable.create {
            val json = objectMapper.writeValueAsString(person)
            val file = storages.createExternalStorageFile("persons", "person.data")

            BufferedOutputStream(FileOutputStream(file)).use {
                it.write(json.toByteArray())
            }
            it.onComplete()

        }).subscribe(
                { with { it.saveComplete() } },
                { throwable ->
                    Log.e(TAG, "Write file failed", throwable)
                    with { it.saveFailed() }
                }
        )
    }

    @SuppressLint("CheckResult")
    override fun loadPerson() {
        val decorator = rxDecoratorBuilder.build()
        decorator.de<Person>(Single.create {
            val file = storages.createExternalStorageFile("persons", "person.data")
            if (!file.exists()) {
                return@create
            }

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
            with { it.showPerson(person) }
        }, { throwable ->
            Log.e(TAG, "Read file failed", throwable)
            with { it.loadFailed() }
        })
    }
}
