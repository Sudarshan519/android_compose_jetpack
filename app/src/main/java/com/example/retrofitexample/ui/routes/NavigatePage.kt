package com.example.retrofitexample.ui.routes

import PostViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.retrofitexample.AudioBook
import com.example.retrofitexample.CircularAvatar
import com.example.retrofitexample.MainContent
import com.example.retrofitexample.Post
import com.example.retrofitexample.ui.pages.AlbumDetailPage

@Composable
fun ListPost(post: Post, onClick: () -> Unit) {
    // Compose your list item UI here

    Row(

        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                onClick()
            }
    ) {

        CircularAvatar(
            imageUrl = post.avatar,
            size = 64.dp
        )
        Column() {
            Row(
                Modifier.padding(start = 10.dp)
            ) {
                Text(text = post.first_name)

                Text(text = post.last_name)
            }
            Text(text = post.email, modifier = Modifier.padding(start = 10.dp))
        }

    }

}


const val TEST_ROUTE = "test_route"
@SuppressLint("SuspiciousIndentation")
@Composable
fun NavigatePage( ) {
 val navHostController = rememberNavController( )
    val viewModel:PostViewModel=PostViewModel()

            NavHost(navController = navHostController, startDestination = "homepage"){
        composable("homepage"){
           MainContent(navController=navHostController,viewModel)
        }
                composable("test"){
                    TestPage(navController = navHostController,viewModel)
                }
                composable("album-detail"){
                    AlbumDetailPage(navController = navHostController,viewModel)
                }
                composable("grid_detail/link{item}",
                    arguments = listOf(
                        navArgument("item"){
                            type = NavType.StringType
                        }
                    )
                ){navBackStackEntry ->

                    navBackStackEntry?.arguments?.getString("item")?.let { json->
                        DetailPage(
                            navController = navHostController,
                            viewModel = viewModel,
                            link = json,
                        )

                    }}
                composable("details/{link}", arguments = listOf(navArgument(name= "link"){   type = NavType.StringType})){
                   navBackStackEntry ->  navBackStackEntry?.arguments?.getString("link")?.let {
                    link->
                    DetailPage(navController = navHostController,viewModel=viewModel, link = link,)
                }

                }
                composable("view_all/{link}", arguments = listOf(navArgument(name= "link"){   type = NavType.StringType})){
                        navBackStackEntry ->  navBackStackEntry?.arguments?.getString("link")?.let {
                        link->
                    ViewAll(navController = navHostController,viewModel=viewModel, link = link,)
                }

                }
        composable("detailPage/{item}",     arguments= listOf (navArgument(name = "item"){
            type= NavType.StringType
        })){
navBackStackEntry ->  navBackStackEntry?.arguments?.getString("item")
            }
        }
    viewModel
        .getHomePage()
    viewModel.getPosts()

    }
//}

@Composable 
fun ViewAll(navController: NavHostController,viewModel:PostViewModel, link :String,){
    val item=viewModel.audioBookList.firstOrNull { item->item.slug==link }
    Column(


    ) {
        if (item != null) {
            item.name?.let { Text(text = it) }
        }
        LazyVerticalGrid(columns = GridCells.Fixed(2), content ={
            if (item != null) {
                item.items?.let { items(it.size){
                    i-> Card(
                    modifier = Modifier.padding(4.dp)
                    ) {
                    AudioBookComponent(navController = navController, audioBook = item.items[i])
                }

                } }
            }
        } )



    }

}

@Composable
fun AudioBookComponent(navController: NavHostController,audioBook: AudioBook){

   Column(
      modifier= Modifier.size(300.dp ),
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       CircularAvatar(imageUrl = "${audioBook.logo}", size = 100.dp)
       audioBook.genre?.let { Text(text = it, modifier = Modifier.align(Alignment.CenterHorizontally)) }
       audioBook.title?.let { Text(text = it, modifier = Modifier.align(Alignment.CenterHorizontally)) }

       audioBook.genre?.let { Text(text ="Genere: "+ it,modifier = Modifier.align(Alignment.CenterHorizontally)) }

   }
}
@Composable
fun GenreRoute(navController: NavHostController){}

@Composable
fun MainPage(navController: NavHostController){}

@Composable
fun TestPage(navController: NavHostController,postViewModel: PostViewModel){
LaunchedEffect(key1 = Unit  ){

        postViewModel.getPosts( )
        Log.d("TAG", "TestPage: LOADING")

}
    val  isloading=postViewModel.isloading.value
  Column() {
      Text(text = "Test",Modifier.clickable {
          postViewModel.getPosts()
      })
//      Text(text =isloading.toString())
      if(!postViewModel.isloading.value) {
          postViewModel.posts.value?.let {post-> post.forEach { item-> ListPost(post = item) {
              
          } }

          }
      }else{
                    CircularProgressIndicator(
              modifier = Modifier.size(48.dp),
              color = Color.Blue, // Optional: customize the color
              strokeWidth = 4.dp, // Optional: customize the stroke width


          )
      }

  }
}



@SuppressLint("SuspiciousIndentation")
@Composable
fun DetailPage(navController: NavHostController,viewModel: PostViewModel,link:String){
    LaunchedEffect(key1 = Unit  ){

        viewModel.getApi(link )
        Log.d("TAG", "TestPage: LOADING $link")

    }

 if (   viewModel.isloading.value) {
     CircularProgressIndicator()
 }
 else
Column(
) {
//    viewModel.audioBooks.forEach{
//        book->
//        book.name?.let { Text(text = it) }
//    }
    viewModel.audioBooks.forEach {i->
        Column(
            modifier = Modifier
                .size(200.dp, 200.dp)
                .fillMaxWidth()
        ) {i.logo?.let { CircularAvatar(imageUrl = it, size =100.dp ) }
            i.name?.let { Text(text = it) }
            Text(text = i.description.toString())

        }
    }

//   if(viewModel.generesDetail.value!=null)
//    Text(viewModel.generesDetail.value.toString())
//    viewModel.  generesDetail.value?.data?.items?.forEach{
//            i->
//        i.name?.let { Text(text = it) }

//    }

}
//Text(text ="${viewModel.audioBookList.value.toString()}")
}