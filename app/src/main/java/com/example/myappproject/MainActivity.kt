package com.example.myappproject

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myappproject.ui.theme.MyAppProjectTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppProjectTheme {
             App()
            }
        }
    }
}

@Composable
fun App() {

    var selectImage by remember { mutableStateOf<List<Uri?>>(emptyList()) }

    val phototaker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia() ,
        onResult = {
            selectImage = it

        }
    )

    Surface (
        modifier = Modifier
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            
            Text(
                text = "Select picture from galery",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            )
            Spacer(modifier = Modifier.size(20.dp))
            
            Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0073E6),
                    contentColor = Color.White
                ),
                onClick = {
                    phototaker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }) {

                Row {

                    Icon(painter = painterResource(id =android.R.drawable.ic_input_add),
                        contentDescription = "Add image")


                    Text(
                        text = "Add a photo",
                        style = TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }
                
            }
            Spacer(modifier = Modifier.size(20.dp))

            Box {
                LazyColumn{
                    items(selectImage){selectImage->
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { },
                            model = selectImage,
                            contentDescription = null,

                            )
                    }
                }
            }
        }
    }


}

@Composable
fun alertDialog() {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(true) }

    if(openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(text = "AlertDialog", color = Color.Black)},
            text ={ Text(text = "You want to delete this image...", color = Color.Black)},

            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        Toast.makeText(context, "Confirm button click", Toast.LENGTH_SHORT).show()
                    }) {
                    Text(text = "Confirm", color = Color.Black)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        Toast.makeText(context,"Dismiss the button click", Toast.LENGTH_SHORT).show()
                    }) {
                    Text(text = "Dismiss", color= Color.Black)

                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    MyAppProjectTheme {
        App()
    }
}