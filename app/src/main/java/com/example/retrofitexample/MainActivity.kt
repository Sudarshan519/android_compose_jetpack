package com.example.retrofitexample

import PostViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitexample.ui.theme.RetrofitExampleTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.retrofitexample.ui.pages.GridItem
import com.example.retrofitexample.ui.routes.NavigatePage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private lateinit var postViewModel: PostViewModel
    @SuppressLint("SuspiciousIndentation")
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          val maxRandomValues = List(100) {
              Random.nextInt()
          }

        val list = (1..100).map { it.toString() }
        setContent {    RetrofitExampleTheme {
            // A surface container using the 'background' color from the theme
            Surface(


                color = MaterialTheme.colorScheme.background
            ) {
            NavigatePage(  )}}
//  MainContent(postViewModel)
        }

        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        postViewModel.getPosts()

        postViewModel.getHomePage()
    }
}
@Composable
fun Body() {

}
@SuppressLint("SuspiciousIndentation")
@Composable
fun MainContent(navController: NavController, postViewModel:PostViewModel) {
    var ctx = LocalContext.current.applicationContext
val page=postViewModel.activePage.value
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    Scaffold(
        bottomBar  = {
            BottonNav(navController,postViewModel)
        }

    ) { contentPadding ->
        // Screen content
        Box(modifier = Modifier
            .padding(contentPadding)

            .fillMaxSize()) {
            Column() {

                if ((!postViewModel.isloading.value))
                    SwipeRefresh(state = swipeRefreshState, onRefresh = { postViewModel.getHomePage() }) {

if(page=="HOME")
                        AudioBookContents(navController ,postViewModel)
else if(page=="SHOP")
    Text(text = "SETTINGS")
//    ShopContents()

else if (page=="SETTINGS")
      Text(text = "SETTINGS")
                        else{
                            Text("PROFILE")
                        }
                    }
        }
    }


}



    @SuppressLint("SuspiciousIndentation")
    @Composable
    fun Greeting(
        viewModel: PostViewModel,
        onClick: () -> Unit,
        name: String,
    ) {

        var isRefreshing by remember { mutableStateOf(false) }
        val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)


        val audioBookList = viewModel.audioBookList
        if (viewModel.audioBookList.isNotEmpty())
            SwipeRefresh(state = swipeRefreshState, onRefresh = { /*TODO*/
                viewModel.getHomePage()
            }) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())

                ) {
                    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                        item {
                            (1..100).map {
                                Text(text = "HELLO")
                            }
                        }
                    }
                    audioBookList.forEach { message ->
                        if (message.slug == "banners")
                            message.items?.map { audiobook ->
                                audiobook.background?.let { it ->
                                    Image(
                                        painter = rememberAsyncImagePainter(it),
                                        contentDescription = null,
                                        modifier = Modifier.size(128.dp)
                                    )
                                }
                            }

                        message.name?.let { it ->
                            if (message.name != "banners")
                                Text(text = it)

                        }
                    }

                }
            }





    @Composable
    fun ListPost(post: Post, onClick: () -> Unit) {
        // Compose your list item UI here
        Row(

            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(10.dp)
                .clickable {
                    onClick()
                },    horizontalArrangement = Arrangement.SpaceBetween
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

}}
@Composable
fun CircularAvatar(imageUrl: String, size: Dp, alpha: Double =1.0,round:Boolean=false ) {
    if(round)
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = null,
        modifier = Modifier
            .size(size).clip(RoundedCornerShape(10.dp)).background(Color.Red)
//            .clip(CircleShape)
    , contentScale = ContentScale.FillBounds,

        alpha = alpha.toFloat()
    )
    else
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(size).clip(RoundedCornerShape(10.dp)).background(Color.Gray)
//            .clip(CircleShape)
            , contentScale = ContentScale.FillBounds,

            alpha = alpha.toFloat()
        )
}


@Composable
fun GroupTitle(navController: NavController,it1:AudioBookList){
    it1.name?.let { Text(text = it) }
    Text(text = "View All", modifier = Modifier.clickable {
        if(it1.name=="Genre")
            navController.navigate("album-detail")
        else
        navController.navigate("view_all/${it1.slug}")
        Log.d("TAG", "MainContent: ${it1.next_page_url}")
    })
}

@Composable
fun BottonNav(navController: NavController,postViewModel: PostViewModel){
    var ctx = LocalContext.current.applicationContext
val activePage=postViewModel.activePage.value
    Box(

  ) {
      Row(
          modifier= Modifier
              .fillMaxWidth()
              .size(60.dp)
              .background(color = Color.White, shape = RoundedCornerShape(4.dp)),  horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically
      ) {
          BottomNavItem(postViewModel,"HOME")
          BottomNavItem(postViewModel,"SHOP")
          BottomNavItem(postViewModel,"SETTINGS")
          BottomNavItem(postViewModel,"PROFILE")
      }
  }
}

@Composable
fun BottomNavItem(viewModel:PostViewModel,title:String){
    if(viewModel.activePage.value==title )
        Text(text = title, color = Color.Black,)
    else
        Text(text = title, color = Color.Gray,  modifier = Modifier.clickable {
            viewModel.activePage.value=title
        })
}


@Composable
fun AudioBookContents(
    navController: NavController,postViewModel:PostViewModel
){
    Column(

        modifier = Modifier
            .verticalScroll(rememberScrollState())


    ) {
        if (postViewModel.audioBookList.isNotEmpty())
            Column() {

                Text(text = "Genre")
                Box(
                    modifier = Modifier


                        .fillMaxWidth()
                        .size(300.dp, 200.dp)

                ) {
                    LazyHorizontalGrid(

                        GridCells.Fixed(1),

                        contentPadding = PaddingValues(
                            start = 12.dp,
                            top = 16.dp,
                            end = 12.dp,
                            bottom = 16.dp
                        ),
                        content = {
                            if (postViewModel.audioBookList.isNotEmpty())
                                postViewModel.audioBookList[2].items?.let {
                                    items(it.size) { index ->
                                        Card(

                                            modifier = Modifier
                                                .padding(4.dp)
//                                                  .fillMaxHeight()
                                                .size(100.dp, 100.dp)
                                                .clickable {
                                                    navController.navigate("test")
                                                    Log.d(
                                                        "TAG",
                                                        "MainContent:${
                                                            postViewModel.audioBookList[2].items?.get(
                                                                index
                                                            )?.redirect_link
                                                        } "
                                                    )
//                                                            navController.navigate(route = "test")
                                                },

                                            ) {
                                            postViewModel.audioBookList[2].items?.get(index)
                                                ?.let { it1 ->
                                                    Column(
                                                        Modifier
                                                            .padding(4.dp),
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                    ) {
                                                        it1.logo?.let { it2 ->
                                                            CircularAvatar(
                                                                imageUrl = it2,
                                                                size = 100.dp
                                                            )
                                                        }
                                                        it1.title?.let { it2 -> Text(text = it2,) }
                                                    }
                                                }
                                        }
                                    }
                                }

                        }
                    )
                }
                postViewModel.audioBookList.forEach { audioBook ->

                    audioBook.let {
                        Column(
                            modifier = Modifier.fillMaxWidth()

                        ) {

                            it?.let { it1 ->
                                it1.name?.let { name ->
                                    if(name=="Banners")
                                        Column( modifier = Modifier


                                            .fillMaxWidth()
                                            .size(300.dp, 200.dp)) {
//                                                    HorizontalPager(pageCount = 10) { page ->
//                                                        // Our page content
//                                                        Text(
//                                                            text = "Page: $page",
//                                                            modifier = Modifier.fillMaxWidth()
//                                                        )
//                                                    }

                                            LazyHorizontalGrid(

                                                GridCells.Fixed(1),

                                                contentPadding = PaddingValues(
                                                    start = 12.dp,
                                                    top = 16.dp,
                                                    end = 12.dp,
                                                    bottom = 16.dp
                                                ),
                                                content = {
                                                    it1.items?.let {
                                                        items(it.size) { index ->
                                                            it1.items[index].logo?.let { it2 ->
                                                                CircularAvatar(
                                                                    imageUrl = it2,
                                                                    size =300.dp
                                                                )
                                                            }
                                                        }}
                                                })
                                        }
                                    else
                                        Column(

                                        ) {
                                        }

  Row(  modifier=Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween){
      GroupTitle(navController ,it1)

        }
                                    Row(
                                        modifier = Modifier
                                            .horizontalScroll(rememberScrollState())
                                            .fillMaxSize()
                                    ){
                                        it1.items?.forEach { a->
                                            GridItem(audioBook = a,)
                                        }
                                    }
                                        }}
//                                            Row(
//                                                modifier=Modifier.fillMaxWidth(),
//                                                horizontalArrangement = Arrangement.SpaceBetween
//                                            ) {
//
//                                                GroupTitle(navController ,it1)
//                                            }
//
//                                            Row(
//                                                modifier = Modifier
//                                                    .horizontalScroll(rememberScrollState())
//                                                    .fillMaxSize()
//                                            ) {
//                                                it1.items?.forEach { a->
//                                                    Column(
//                                                        modifier = Modifier
//                                                            .size(300.dp, 200.dp)
//                                                            .clickable {
////                                                           a.redirect_link?.let { it2 ->
////                                                               postViewModel.getApi(
////                                                                   it2
////                                                               )
////                                                           };
//                                                                val url = URLEncoder.encode(
//                                                                    "${a.redirect_link}",
//                                                                    StandardCharsets.UTF_8.toString()
//                                                                )
//
//                                                                navController.navigate("details/$url")
////                                                           a.redirect_link?.let { a->  navController.navigate("grid_detail/$a" ) }
////                                                           a.redirect_link?.let { it2 ->
////                                                               navController.navigate("details/{$it2}" )
////                                                           }
//                                                                Log.d(
//                                                                    "TAG",
//                                                                    "MainContent:${a.redirect_link.toString()} "
//                                                                )
//                                                            }
//                                                    ) {
//                                                        a.logo?.let { it2 -> CircularAvatar(imageUrl = it2, size = 100.dp) }
//                                                        a.background?.let { it2 -> CircularAvatar(
//                                                            imageUrl = it2, size = 100.dp) }
//                                                        a.name?.let { it2 -> Text(text=it2)
//                                                            a.contentProvider?.let { b-> b.name?.let { it3 -> Text(text= it3) } }
//                                                        }
//                                                        a.title?.let { it2 -> Text(text= it2, ) }
//                                                        a.description?.let { it2 -> Text(text= it2, ) }
//                                                    }
//                                                }
//                                            }
//
//                                        }
//

//                                }
                            }
                        }
                    }
                }
            }



    }


