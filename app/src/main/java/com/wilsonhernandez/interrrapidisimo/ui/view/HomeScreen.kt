package com.wilsonhernandez.interrrapidisimo.ui.view

import android.annotation.SuppressLint
import android.os.Build
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wilsonhernandez.interrrapidisimo.data.network.response.BoardResponse
import com.wilsonhernandez.interrrapidisimo.ui.theme.Orange
import com.wilsonhernandez.interrrapidisimo.ui.viewmodel.HomeViewModel

@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val isLoading: Boolean by viewModel.isLoading.observeAsState(false)
    val listBoard: List<BoardResponse> by viewModel.listBoard.observeAsState(emptyList())
    val listName: List<String> by viewModel.listName.observeAsState(emptyList())
    val filter: String by viewModel.filter.observeAsState("")
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Interrapidisimo") },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Orange,
                titleContentColor = Color.Black
            )
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 70.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (viewModel.listNameBoard.isNotEmpty()) {
                ListBoard(listBoard = listName, value = filter, onTextChanged = {
                    viewModel.listFilter(it)
                })
            } else {
                if (listBoard.isNotEmpty()) {
                    CreateBoard(amountBoard = listBoard.size)
                } else {
                    if (isLoading) {
                        progressLoad()
                    } else {
                        Button(onClick = {
                            viewModel.getData()
                        })
                    }
                }
            }


        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Button(onClick: () -> Unit) {
    val selected = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (selected.value) 0.8f else 1f, label = "")
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .scale(scale.value)
            .height(60.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        selected.value = true
                        onClick.invoke()
                    }

                    MotionEvent.ACTION_UP -> {
                        selected.value = false
                    }
                }
                true
            }
    ) {
        Text(text = "Consutar Tablas", fontSize = 15.sp, color = Color.White)
    }
}

@Composable
fun progressLoad() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        CircularProgressIndicator(color = Color.Black)
        Text(text = "Cargando...")
    }
}

@Composable
fun CreateBoard(amountBoard: Int) {
    Column(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "La consulta fue exitosa",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Se encotraron $amountBoard tablas las cuales van hacer creada en el gestor de base de datos sqlite")
        Spacer(modifier = Modifier.height(40.dp))
        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
fun ListBoard(listBoard: List<String>, value: String, onTextChanged: (String) -> Unit) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box {
                Column {
                    Text(
                        text = "Lista de tablas creadas",
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                    Text(text = "Filtrar por nombre")
                    TextField(value = value, onTextChanged = { onTextChanged.invoke(it) })
                }
                if (listBoard.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.padding(top = 130.dp)) {
                        items(listBoard.size) {
                            Item(listBoard[it])
                            Spacer(
                                modifier = Modifier
                                    .height(3.dp)
                                    .background(color = Color.Black)
                            )
                        }
                    }
                } else {
                    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(text = "No hay informacion")
                    }
                }

            }
        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(value: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onTextChanged.invoke(it) },
        maxLines = 1,
        textStyle = TextStyle(color = Color.Black),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),

        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
    )

}

@Composable
fun Item(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
            Row {
                Text(
                    text = name,
                    style = TextStyle(color = Color.Black)
                )
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 30)
@Composable
fun ItemPreview() {
    Item("Prueba")
}