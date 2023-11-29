package com.wilsonhernandez.interrrapidisimo.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wilsonhernandez.interrrapidisimo.data.network.response.BoardResponse
import com.wilsonhernandez.interrrapidisimo.domain.CreateBoardSqliteUseCase
import com.wilsonhernandez.interrrapidisimo.domain.GetDataBoardsSqliteUseCase
import com.wilsonhernandez.interrrapidisimo.domain.GetDataBoardsUseCase
import com.wilsonhernandez.interrrapidisimo.domain.SetDataBoardUseCase
import com.wilsonhernandez.interrrapidisimo.util.NetworkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.P)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDataBoardsUseCase: GetDataBoardsUseCase,
    private val networkConnectivity: NetworkConnectivity,
    private val createBoardSqliteUseCase: CreateBoardSqliteUseCase,
    private val setDataBoardUseCase: SetDataBoardUseCase,
    private val getDataBoardsSqliteUseCase: GetDataBoardsSqliteUseCase
) : ViewModel() {
    private val _alertNetwork = MutableLiveData<Boolean>()
    val alertNetwork: LiveData<Boolean> = _alertNetwork

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listBoard = MutableLiveData<List<BoardResponse>>()
    val listBoard: LiveData<List<BoardResponse>> = _listBoard

    private val _listName = MutableLiveData<List<String>>()
    val listName: LiveData<List<String>> = _listName

    private val _filter = MutableLiveData<String>()
    val filter: LiveData<String> = _filter

     var listNameBoard : List<String> = listOf()
    init {
        getListData()
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun getData() {
        _isLoading.postValue(true)
        if (!networkConnectivity.checkNetworkConnectivity()) {
            _alertNetwork.postValue(true)
            return
        }

        viewModelScope.launch {
            getDataBoardsUseCase.invoke(success = { listBoard ->
                _isLoading.postValue(false)
                _listBoard.postValue(listBoard)
                val countBoard = listBoard.size
                for (data in listBoard) {
                    runBlocking {
                        createBoardSqliteUseCase.invoke(data.nameBoard, data.query, isCreate = {
                            if (it){
                                setDataBoardUseCase.invoke(name = data.nameBoard, pk = data.pk, numberField = data.numberFields, dateUpdate = data.dateUpdate)
                            }
                        })
                    }


                }
                getListData()
            }, failed = {
                _isLoading.postValue(false)
            })
        }
    }

   @RequiresApi(Build.VERSION_CODES.P)
   private fun getListData(){
       _isLoading.postValue(true)
       viewModelScope.launch {
           getDataBoardsSqliteUseCase.invoke(success = {
               _listName.postValue(it)
               listNameBoard=it
               _isLoading.postValue(false)
           })
       }
    }

    fun listFilter(filter:String){
        _filter.postValue(filter)
        if (filter.length>2){
            val listFilter = _listName.value?.filter { it->it.contains(filter,ignoreCase = true) }
            _listName.postValue(listFilter)
        }else{
            _listName.postValue(listNameBoard)
        }
    }
}