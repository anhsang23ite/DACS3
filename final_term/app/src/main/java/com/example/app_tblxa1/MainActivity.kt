package com.example.app_tblxa1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.app_tblxa1.ui.theme.APP_TBLXA1Theme
import androidx.compose.ui.res.painterResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APP_TBLXA1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF3F4F6)
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "GPLX Hạng A1",
                color = Color(0xFF2563EB),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = Color(0xFF2563EB),
                modifier = Modifier.size(24.dp)
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardItem(imageResId = R.drawable.book, text = "Học lý thuyết")
            CardItem(imageResId = R.drawable.directionscar, text = "Thi sát hạch")
            CardItem(imageResId = R.drawable.traffic, text = "Biển báo")
            CardItem(imageResId = R.drawable.lightbulb,"Mẹo thi")
            CardItem(imageResId = R.drawable.email, "Các câu hỏi")
        }
    }
}

@Composable
fun CardItem(imageResId: Int, text: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = text,
            modifier = Modifier.size(48.dp)
        )
        Text(text = text)
    }
}