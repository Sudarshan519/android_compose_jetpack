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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainContent(navController: NavController, postViewModel:PostViewModel) {
    var ctx = LocalContext.current.applicationContext

    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)



    if ((!postViewModel.isloading.value))
        SwipeRefresh(state = swipeRefreshState, onRefresh = { postViewModel.getHomePage() }) {


            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()

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

                                        Column(

                                        ) {  it1.name?.let { name ->

                                              Text(text = name)

                                            Row(

                                            ) {
                                                it1.items?.forEach { a->
                                                   Column(
                                                       modifier = Modifier.clickable {
//                                                           a.redirect_link?.let { it2 ->
//                                                               postViewModel.getApi(
//                                                                   it2
//                                                               )
//                                                           };
                                                        val   url= URLEncoder.encode("${a.redirect_link}", StandardCharsets.UTF_8.toString())
                                                           navController.navigate("details/$url")
//                                                           a.redirect_link?.let { a->  navController.navigate("grid_detail/$a" ) }
//                                                           a.redirect_link?.let { it2 ->
//                                                               navController.navigate("details/{$it2}" )
//                                                           }
                                                           Log.d(
                                                               "TAG",
                                                               "MainContent:${a.redirect_link.toString()} "
                                                           ) }
                                                   ) {
                                                       a.logo?.let { it2 -> CircularAvatar(imageUrl= it2, size = 100.dp) }
                                                       a.background?.let { it2 -> CircularAvatar(imageUrl= it2, size = 100.dp) }
                                                       a.name?.let { it2 -> Text(text=it2)
                                                           a.contentProvider?.let { b-> b.name?.let { it3 -> Text(text= it3) } }
                                                       }
                                                       a.title?.let { it2 -> Text(text= it2, ) }
                                                       a.description?.let { it2 -> Text(text= it2, ) }
                                                   }
                                                }
                                            }
//
                                        //                                            if(audioBook.slug=="banners")
//                                                audioBook.items?.map { ab ->
//                                                    ab.background?.let { it ->
//                                                        Image(
//                                                            painter = rememberAsyncImagePainter(it),
//                                                            contentDescription = null,
//                                                            modifier = Modifier.size(128.dp)
//                                                        )
//                                                    }
//                                                }
//
//                                            }

//                                        it.items?.let { it1 -> it.items.map {
//
//                                                audioBook ->
//                                            audioBook?.let { it2 -> Column() {
//                                                it2.name?.let { it3 -> Text(text = it3) }
//                                                it2.background?.let { it3 ->      Image(
//                                                    painter = rememberAsyncImagePainter(it3),
//                                                    contentDescription = null,
//                                                    modifier = Modifier.size(128.dp)
//                                                ) }
//                                            } }
//                                        }
                                        }
//                                    }

                                    }
                                }
                            }
                        }
                    }
            }


//}


//        }
//    }
        }

    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Greeting(
        viewModel: PostViewModel,
        onClick: () -> Unit,
        name: String,
        modifier: Modifier = Modifier
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
                        .fillMaxSize()
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

//                    if(message.slug== "genre")
//                        LazyVerticalStaggeredGrid(
//                            columns = StaggeredGridCells.Fixed(3),
//
//                            horizontalArrangement = Arrangement.spacedBy(4.dp),
//                            content = {
//
////
//                                      //                                items(randomSizedPhotos) { photo ->
////                                    AsyncImage(
////                                        model = photo,
////                                        contentScale = ContentScale.Crop,
////                                        contentDescription = null,
////                                        modifier = Modifier.fillMaxWidth().wrapContentHeight()
////                                    )
////                                }
//                            },
//                            modifier = Modifier.fillMaxSize()
//                        )
//                    else
//                   Row(modifier = Modifier
//                       .horizontalScroll(rememberScrollState())
//                   ) {
//                       message.items?.forEach {
//                               audioBook ->
//
//                           audioBook.let {it ->
//                               Column(
//                                   modifier = Modifier.width(128.dp)
//                               ) {
//                                   Image(
//                                       painter = rememberAsyncImagePainter(it.logo),
//                                       contentDescription = null,
//                                       modifier = Modifier.size(128.dp)
//                                   )
//                                   it.name?.let { it1 -> Text(text = "Name:$it1") }
//                                   it.prices?.let { it1 -> Text(text = "Price:$it1") }
//                                   it.genre?.let { it1 -> Text(text = "Price:$it1") }
//                                   it.contentProvider?.let { abc -> Text(text = "Price:${abc.name}") }
//                               }
//
//                           }
//
//                   }
//                }}
//            }
//          Text(
//              text = "Hello $name!"+viewModel.audiobookvalue.value,
//              modifier = modifier
//                  .clickable(
//                      onClick = onClick
//                  )
//                  .padding(end = 10.dp))
//
//          CircularProgressIndicator(
//              modifier = Modifier.size(48.dp),
//              color = Color.Blue, // Optional: customize the color
//              strokeWidth = 4.dp, // Optional: customize the stroke width
//
//
//          )
                        }
                    }
//                else
//        SwipeRefresh(state =swipeRefreshState , onRefresh = { /*TODO*/
//        viewModel.getPosts()
//        }) {
//
//
//           LazyColumn(
//
//         ) {
//
//
//
//           items(viewModel.items.size) { item ->
//               ListItem(post = viewModel.items[item], onClick = onClick)
//           }
//       }
//        }

//    Text(
//        text = "Hello $name!"+viewModel.text.value,
//        modifier = modifier.clickable (
//            onClick=onClick
//        ),
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

}}
@Composable
fun CircularAvatar(imageUrl: String, size: Dp) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = null,
        modifier = Modifier
            .size(size)
//            .clip(CircleShape)
    , contentScale = ContentScale.FillHeight
    )
}
//private suspend fun performRefreshAction() {
//    // Simulate refresh action delay
//    kotlinx.coroutines.delay(2000)
//    // Perform actual refresh action here
//}
//@Composable
//fun Scaffold(
//    modifier: Modifier = Modifier,
//    topBar: @Composable () -> Unit = {},
//    bottomBar: @Composable () -> Unit = {},
//    snackbarHost: @Composable () -> Unit = {},
//    floatingActionButton: @Composable () -> Unit = {},
//    containerColor: Color = MaterialTheme.colorScheme.background,
//    contentColor: Color = contentColorFor(containerColor),
//    content: @Composable (PaddingValues) -> Unit
//) {
//}