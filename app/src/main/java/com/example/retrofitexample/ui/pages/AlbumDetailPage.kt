package com.example.retrofitexample.ui.pages

import PostViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retrofitexample.AudioBook
import com.example.retrofitexample.CircularAvatar

@SuppressLint("SuspiciousIndentation")
@Composable
fun AlbumDetailPage(navController: NavController,postViewModel: PostViewModel){
    val item=postViewModel.audioBookList.firstOrNull { item->item.name=="Genre" }
    var selected by remember{ mutableStateOf(0) };
//    var name by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit  ){
        if (item != null) {
            item.items?.get(0)?.redirect_link?.let { it1 -> postViewModel.getApi(it1) }
        }
//        postViewModel.getPosts( )
        Log.d("TAG", "TestPage: LOADING")

    }
   Scaffold(
       topBar = {
           TopAppBar(
               backgroundColor = Color.Red,
               content = {
               Text(text = "Albums", color = Color.Red)
           })
       }
   ) {

           contentPadding   -> Box(modifier = Modifier.padding(contentPadding)) {

     Row {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(0.dp, 10.dp)
    ) {
//        Text(text = "ALBUM")
//
//        Text(text = "ALBUM")
//        Text(text = "ALBUM")
//        Text(text = "ALBUM")
//        Text(text = "ALBUM")
//        Text(text = "ALBUM")
        if (item != null) {
            item.items?.forEach {
                data->
                data.logo?.let {

                    if(item.items[selected]==data)

                        Box(modifier = Modifier
                            .padding(6.dp)
                            .clickable {
                                selected = item.items.indexOf(data)
                                data.redirect_link?.let { it1 -> postViewModel.getApi(it1) }
                                Log.d("TAG", "AlbumDetailPage: ${data.redirect_link}")
                            }
                            .border(
                                width = 1.dp, color = Color.Red,
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            CircularAvatar(imageUrl = it, size = 80.dp,)
                        }


                else
                     Box(modifier = Modifier
                         .padding(6.dp)
                         .clickable {
                             selected = item.items.indexOf(data)
                             data?.redirect_link?.let { it1 ->
                                 postViewModel.getApi(
                                     it1
                                 )
                             }
                         }
                         .border(
                             width = 1.dp, color = Color.White,
                             shape = RoundedCornerShape(5.dp)
                         )) {
                         CircularAvatar(imageUrl = it, size = 80.dp, alpha = 0.5)
                     }
                }
            }
        }
    }
         Divider(
             color = Color.Gray,
             modifier = Modifier
                 .fillMaxHeight()
                 .padding(6.dp, 6.dp) //fill the max height
                 .width(1.dp)
         )
        if (postViewModel.isloading.value)
            CircularProgressIndicator()
         else
         Column(

//             modifier = Modifier.verticalScroll(rememberScrollState())
         ) {
             Text(text = "Album Detail")
             Text(text =  postViewModel.audioBooks.size.toString())
             LazyVerticalGrid(columns = GridCells.Fixed(2),) {
                 items(postViewModel.audioBooks.size) { item ->
                     GridItem( postViewModel.audioBooks[item],rounded=true)
                 }
             }
//             LazyVerticalGridInColumn()
//             if (item != null) {
//                 item.items?.get(selected)?.let { it.redirect_link?.let { it1 -> Text(text = it1) } }
//             }
//             Text(text = postViewModel.audioBooks.toString())
//            Box(modifier = Modifier.fillMaxSize()) {
//                LazyVerticalGrid(columns = GridCells.Fixed(2)){
//                    item {
////                        (1..100) .map {i->FruitDataGridItem( data = "dfkslfjklsd",navController)  }
//                    }
//                }
//             if (item != null)
//                 LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier
//                     .fillMaxSize()
//                     .verticalScroll(
//                         rememberScrollState()
//                     )) {
//                   item {postViewModel.audioBooks.map{
//                       it.name?.let { it1 -> Text(text = it1) }
//                   }
//                         {
//                             audioBook.name.let {
//                                 if (it != null) {
//                                     Text(text = it)
//                                 }
//                             }
//                         }
//                     postViewModel.audioBooks?.let {
//                         items(it.size) { item ->
//                         it[item].name?.let { it1 -> Text(text = it1) }
//                         //GridItem(item = item)
//                         }
//                     }

}
//                            postViewModel.audioBooks.forEach { Text(text = it.toString()) }
//            }
         }
     }
   }

}



@Composable
fun LazyVerticalGridInColumn() {
    val items = (1..20).toList() // Example list of items

    Column(Modifier.fillMaxSize()) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(items.size) { item ->
//                GridItem(item = item)
            }
        }
    }
}

@Composable
fun GridItem( audioBook:  AudioBook ,rounded:Boolean=false) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(200.dp)
//            .background(Color.Gray)
    ) {
      Column(

      ) {
          audioBook.logo?.let { CircularAvatar(imageUrl = it, size =160.dp ) }
        Row(
            verticalAlignment = Alignment.CenterVertically

        ) {
            Box(modifier = Modifier
                .size(20.dp, 20.dp)
                .background(Color.Red)) {
                Text(text = "*")
            }
            audioBook?.let { Text(text = "4*") }
            audioBook .title?.let {
                Text(
                    text = it,

                    color = Color.Red
                )
            }
        }
        }
    }
}