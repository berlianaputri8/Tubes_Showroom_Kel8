package id.ac.unpas.showroom.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.ac.unpas.showroom.model.Mobil
import id.ac.unpas.showroom.model.bahanBakar
import id.ac.unpas.showroom.repositories.MobilRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MobilViewModel @Inject constructor(private val mobilRepository: MobilRepository) : ViewModel()
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
    private val _list: MutableLiveData<List<Mobil>> =
        MutableLiveData()
    val list: LiveData<List<Mobil>> get() = _list
    suspend fun loadItems() {
        _isLoading.postValue(true)
        mobilRepository.loadItems(onSuccess = {
            _isLoading.postValue(false)
            _list.postValue(it)
        }, onError = { list, message ->
            _toast.postValue(message)
            _isLoading.postValue(false)
            _list.postValue(list)
        })
    }
    suspend fun insert(
        merk: String,
        model: String,
        bahan_bakar: bahanBakar,
        dijual: String,
        deskripsi: String
    ){
        _isLoading.postValue(true)
        mobilRepository.insert(
            merk, model, bahan_bakar,dijual,deskripsi,
            onError = { item, message ->
                _toast.postValue(message)
                _isLoading.postValue(false)
            }, onSuccess = {
                _isLoading.postValue(false)
                _success.postValue(true)
            }
        )
    }

    suspend fun loadItem(id: String, onSuccess: (Mobil?) ->
    Unit) {
        val item = mobilRepository.find(id)
        onSuccess(item)
    }
    suspend fun update(
        id: String,
        merk : String,
        model : String,
        bahan_bakar : bahanBakar,
        dijual : String,
        deskripsi : String
    ){
        _isLoading.postValue(true)
        mobilRepository.update(
            id, merk, model, bahan_bakar, dijual, deskripsi,
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
        mobilRepository.delete(id, onError = { message ->
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