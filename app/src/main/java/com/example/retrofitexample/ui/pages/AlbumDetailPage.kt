package com.example.retrofitexample.ui.pages

import PostViewModel
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retrofitexample.CircularAvatar

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
   Scaffold() {
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
                            CircularAvatar(imageUrl = it, size = 80.dp)
                        }


                else
                     Box(modifier = Modifier
                         .padding(6.dp)
                         .clickable {
                             selected = item.items.indexOf(data)
                         }
                         .border(
                             width = 1.dp, color = Color.White,
                             shape = RoundedCornerShape(5.dp)
                         )) {
                         CircularAvatar(imageUrl = it, size = 80.dp)
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
         Column() {
             Text(text = "Album Detail")
             Text(text = selected.toString())
             if (item != null) {
                 item.items?.get(selected)?.let { it.redirect_link?.let { it1 -> Text(text = it1) } }
             }
             Text(text = postViewModel.audioBooks.toString())
             postViewModel.audioBooks.forEach { Text(text = it.toString()) }
         }
     }
   }
   }
}