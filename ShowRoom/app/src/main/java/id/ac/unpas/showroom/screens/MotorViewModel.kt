package id.ac.unpas.showroom.screens
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.unpas.showroom.model.Motor
import id.ac.unpas.showroom.repositories.MotorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MotorViewModel @Inject constructor(private val motorRepository: MotorRepository) : ViewModel()
{
    private val _isLoading: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _success: MutableLiveData<Boolean> =
        MutableLiveData(false)
    val success: LiveData<Boolean> get() = _success
    private val _toast: MutableLiveData<String> =
        MutableLiveData()
    val toast: LiveData<String> get() = _toast
    private val _list: MutableLiveData<List<Motor>> =
        MutableLiveData()
    val list: LiveData<List<Motor>> get() = _list
    suspend fun loadItems() {
        _isLoading.postValue(true)
        motorRepository.loadItems(onSuccess = {
            _isLoading.postValue(false)
            _list.postValue(it)
        }, onError = { list, message ->
            _toast.postValue(message)
            _isLoading.postValue(false)
            _list.postValue(list)
        })
    }
    suspend fun insert(
        model: String,
        warna: String,
        kapasitas: String,
        tanggal_rilis: String,
        harga: String
    ){
        _isLoading.postValue(true)
        motorRepository.insert(
            model, warna, kapasitas, tanggal_rilis,harga,
            onError = { item, message ->
                _toast.postValue(message)
                _isLoading.postValue(false)
            }, onSuccess = {
                _isLoading.postValue(false)
                _success.postValue(true)
            }
        )
    }

    suspend fun loadItem(id: String, onSuccess: (Motor?) ->
    Unit) {
        val item = motorRepository.find(id)
        onSuccess(item)
    }
    suspend fun update(
        id: String,
        model: String,
        warna: String,
        kapasitas: String,
        tanggal_rilis : String,
        harga : String
    ){
        _isLoading.postValue(true)
        motorRepository.update(
            id, model, warna, kapasitas, tanggal_rilis, harga,
            onError = { item, message ->
                _toast.postValue(message)
                _isLoading.postValue(false)
            }, onSuccess = {
                _isLoading.postValue(false)
                _success.postValue(true)
            }
        )
    }

    suspend fun delete(id: String) {
        _isLoading.postValue(true)
        motorRepository.delete(id, onError = { message ->
            _toast.postValue(message)
            _isLoading.postValue(false)
            _success.postValue(true)
        }, onSuccess = {
            _toast.postValue("Data berhasil dihapus")
            _isLoading.postValue(false)
            _success.postValue(true)
        })
    }
}