package id.ac.unpas.showroom.repositories

import com.benasher44.uuid.uuid4
import id.ac.unpas.showroom.model.Motor
import id.ac.unpas.showroom.networks.MotorApi
import id.ac.unpas.showroom.persistences.MotorDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import com.skydoves.whatif.whatIfNotNull
import javax.inject.Inject

class MotorRepository @Inject constructor(
    private val api: MotorApi,
    private val dao: MotorDao
) : Repository {
    suspend fun loadItems(
        onSuccess: (List<Motor>) -> Unit,
        onError: (List<Motor>, String) -> Unit
    ) {
        val list: List<Motor> = dao.getList()
        api.all()
// handle the case when the API request gets a success response.
            .suspendOnSuccess {
                data.whatIfNotNull {
                    it.data?.let { list ->
                        dao.insertAll(list)
                        val items: List<Motor> = dao.getList()
                        onSuccess(items)
                    }
                }
            }
// handle the case when the API request gets an error response.
// e.g. internal server error.
            .suspendOnError {
                onError(list, message())
            }
// handle the case when the API request gets an exception response.
// e.g. network connection error.
            .suspendOnException {
                onError(list, message())
            }
    }
    suspend fun insert(
        model: String,
        warna: String,
        kapasitas: String,
        tanggal_rilis : String,
        harga : String,
        onSuccess: (Motor) -> Unit,
        onError: (Motor?, String) -> Unit
    ) {
        val id = uuid4().toString()
        val item = Motor(id, model, warna, kapasitas, tanggal_rilis, harga)
        dao.insertAll(item)
        api.insert(item)
// handle the case when the API request gets a success response.
            .suspendOnSuccess {
                onSuccess(item)
            }
// handle the case when the API request gets an rror response.
// e.g. internal server error.
            .suspendOnError {
                onError(item, message())
            }
// handle the case when the API request gets an xception response.
// e.g. network connection error.
            .suspendOnException {
                onError(item, message())
            }
    }
    suspend fun update(
        id : String,
        model: String,
        warna: String,
        kapasitas: String,
        tanggal_rilis : String,
        harga : String,
        onSuccess: (Motor) -> Unit,
        onError: (Motor?, String) -> Unit
    ) {
        val item = Motor(id, model, warna, kapasitas, tanggal_rilis, harga)
        dao.insertAll(item)
        api.update(id, item)
// handle the case when the API request gets a success response.
            .suspendOnSuccess {
                onSuccess(item)
            }
// handle the case when the API request gets an rror response.
// e.g. internal server error.
            .suspendOnError {
                onError(item, message())
            }
// handle the case when the API request gets an exception response.
// e.g. network connection error.
            .suspendOnException {
                onError(item, message())
            }
    }

    suspend fun delete(
        id: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        dao.delete(id)
        api.delete(id)
// handle the case when the API request gets a uccess response.
            .suspendOnSuccess {
                data.whatIfNotNull {
                    onSuccess()
                }
            }
// handle the case when the API request gets an error response.
// e.g. internal server error.
            .suspendOnError {
                onError(message())
            }
// handle the case when the API request gets an exception response.
// e.g. network connection error.
            .suspendOnException {
                onError(message())
            }
    }

    suspend fun find(id: String) : Motor? {
        return dao.find(id)
    }
}
