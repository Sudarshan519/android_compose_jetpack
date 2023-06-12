import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitexample.AudioBook
import com.example.retrofitexample.AudioBookList
import com.example.retrofitexample.BaseResponse
import com.example.retrofitexample.GenereData
import com.example.retrofitexample.MomoClient
import com.example.retrofitexample.Post
import com.example.retrofitexample.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.http.GET
import retrofit2.http.Url

class PostViewModel : ViewModel() {
    private val momoService = MomoClient.apiService
val isloading= mutableStateOf(false)
    private val apiService = RetrofitClient.apiService
    val text = mutableStateOf("Initial Text")
    private val postslist = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = postslist
    val items = mutableStateListOf<Post>()
    var audioBookList= mutableStateListOf<AudioBookList>()
    var audioBooks= mutableStateListOf<AudioBook>()
    val audiobookvalue = mutableStateOf<BaseResponse>(BaseResponse())
    private val baseResponse = MutableLiveData<BaseResponse>()
    val audioBook:LiveData<BaseResponse> get()=baseResponse
    private val generell = MutableLiveData<GenereData>()
    val generesDetail:LiveData<GenereData> get()=generell

fun getHomePage(){
    text.value="loading"
isloading.value=(true)
    audioBookList.clear()
    viewModelScope.launch (Dispatchers.IO){
    try {
        val response=momoService.getHome();
        Log.d("TAG", "getHomePage: $response")
isloading.value=false
audiobookvalue.value=response
        response.data?.items?.let { audioBookList.addAll(it) }
        isloading.value=false
        Log.d("TAG", "getHomePage: ${audiobookvalue.value}")
//        var data=response.data
//        if (data != null) {
//            Log.d("TAG", "getHomePage: ${data.name}")
//        }
    }
    catch (e: Exception) {
        // Handle exception
    }
    }

}
    fun getPosts() {

        text.value="loading"
        isloading.value=true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                items.clear()
                val response = apiService.getPosts()
                Log.d("TAG", "getPosts: "+response)
                    val posts = response
                    postslist.postValue(posts.data)
                text.value=posts.data.toString()
                items.addAll(posts.data)
                isloading.value=false


            } catch (e: Exception) {
                // Handle exception
            }
        }
    }

  fun  getApi(url:String){
 viewModelScope.launch (Dispatchers.IO){
     isloading.value=true
     val response=  momoService.getApi(url)
     response.data?.items?.let {it-> audioBooks   .addAll(it) }
     isloading.value=false

//generell.value=response
//     Log.d("TAG", "api: "+response)
 }

  }
}
