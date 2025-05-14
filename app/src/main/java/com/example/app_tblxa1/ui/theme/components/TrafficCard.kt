package com.example.app_tblxa1.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.app_tblxa1.model.TrafficSign

@Composable
fun TrafficCard(
    trafficSign: TrafficSign,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Sử dụng Coil để tải ảnh từ URL
            Image(
                painter = rememberAsyncImagePainter(
                    model = trafficSign.image_url
                ),
                contentDescription = trafficSign.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )


            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = trafficSign.title,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = trafficSign.description,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}