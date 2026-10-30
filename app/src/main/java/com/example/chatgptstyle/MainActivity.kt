package com.example.chatgptstyle

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent { AppScreen() }
 }
}
@Composable
fun AppScreen() {
 val dark = isSystemInDarkTheme()
 val colors = if (dark) darkColorScheme(primary=Color(0xFF19C79A)) else lightColorScheme(primary=Color(0xFF10A37F))
 MaterialTheme(colorScheme=colors) {
  val context = androidx.compose.ui.platform.LocalContext.current
  var shown by remember { mutableStateOf(false) }
  var info by remember { mutableStateOf(false) }
  LaunchedEffect(Unit) { shown=true }
  Column(Modifier.fillMaxSize().padding(horizontal=18.dp)) {
   Row(Modifier.fillMaxWidth().padding(top=10.dp), verticalAlignment=Alignment.CenterVertically) {
    IconButton(onClick={ info=true }) { Icon(Icons.Default.Menu, contentDescription="菜单") }
    Text("ChatGPT", fontSize=20.sp, fontWeight=FontWeight.SemiBold, modifier=Modifier.weight(1f))
    IconButton(onClick={ openChatGPT(context) }) { Icon(Icons.Default.Add, contentDescription="新建") }
   }
   Spacer(Modifier.weight(1f))
   AnimatedVisibility(shown, enter=fadeIn()+slideInVertically { it/5 }) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment=Alignment.CenterHorizontally) {
     Box(Modifier.size(60.dp).background(MaterialTheme.colorScheme.surfaceVariant,CircleShape), contentAlignment=Alignment.Center) {
      Icon(Icons.Default.ChatBubbleOutline, null, tint=MaterialTheme.colorScheme.primary, modifier=Modifier.size(30.dp))
     }
     Spacer(Modifier.height(18.dp))
     Text("有什么可以帮忙的？", fontSize=25.sp, fontWeight=FontWeight.Medium)
     Spacer(Modifier.height(8.dp))
     Text("使用你的 ChatGPT 账号继续", color=MaterialTheme.colorScheme.onSurfaceVariant)
     Spacer(Modifier.height(24.dp))
     Button(onClick={ openChatGPT(context) }, shape=RoundedCornerShape(24.dp)) {
      Icon(Icons.Default.Language, null); Spacer(Modifier.width(8.dp)); Text("登录并打开 ChatGPT")
     }
     Spacer(Modifier.height(10.dp))
     Text("登录由 ChatGPT 官方网站处理", fontSize=12.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
    }
   }
   Spacer(Modifier.weight(1f))
   Surface(Modifier.fillMaxWidth().padding(bottom=10.dp), shape=RoundedCornerShape(26.dp), color=MaterialTheme.colorScheme.surfaceVariant) {
    Column(Modifier.padding(15.dp)) {
     Text("登录后在官方 ChatGPT 页面中聊天", color=MaterialTheme.colorScheme.onSurfaceVariant, fontSize=13.sp)
     Spacer(Modifier.height(12.dp))
     Row(verticalAlignment=Alignment.CenterVertically) {
      Text("向 ChatGPT 发送消息", modifier=Modifier.weight(1f), color=MaterialTheme.colorScheme.onSurfaceVariant)
      IconButton(onClick={ openChatGPT(context) }) { Icon(Icons.Default.ArrowUpward, "打开 ChatGPT", tint=MaterialTheme.colorScheme.primary) }
     }
     Row {
      IconButton(onClick={ openChatGPT(context) }) { Icon(Icons.Default.Add, "添加") }
      IconButton(onClick={ openChatGPT(context) }) { Icon(Icons.Default.Photo, "图片") }
      IconButton(onClick={ openChatGPT(context) }) { Icon(Icons.Default.Mic, "语音") }
     }
    }
   }
   Text("第三方启动界面，并非 OpenAI 官方应用。账号、聊天和订阅由 chatgpt.com 官方服务处理。", fontSize=10.sp, color=MaterialTheme.colorScheme.onSurfaceVariant, modifier=Modifier.padding(bottom=12.dp))
  }
  if(info) AlertDialog(onDismissRequest={info=false}, title={Text("关于此应用")}, text={Text("此应用不会收集你的 ChatGPT 密码。点击登录后会打开 chatgpt.com，由官方页面完成登录和聊天。它不具备官方 Android App 的全部原生功能。")}, confirmButton={TextButton(onClick={info=false}) { Text("知道了") }})
 }
}
private fun openChatGPT(context: Context) {
 val uri=Uri.parse("https://chatgpt.com/")
 try { CustomTabsIntent.Builder().setShowTitle(true).build().launchUrl(context,uri) }
 catch (_: Exception) { context.startActivity(Intent(Intent.ACTION_VIEW,uri)) }
}
