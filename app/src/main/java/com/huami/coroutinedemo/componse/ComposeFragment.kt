package com.huami.coroutinedemo.componse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.huami.coroutinedemo.componse.ui.theme.CoroutineDemoTheme

/**
 * @date 2022/1/20
 * @author hezhanghe@zepp.com
 * @desc
 */
class ComposeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                Surface(color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(vertical = 4.dp , horizontal = 8.dp)
                ) {
                    Greeting(name = "Android")
                }
            }
        }
    }

    @Composable
    private fun Greeting(name: String) {
        val expanded = remember {
            mutableStateOf(false)
        }
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(onClick = {
                expanded.value = expanded.value.not()
            }) {
                Text(text = if (expanded.value) "Show less" else "Show more")
            }
        }
    }

    @Preview(showBackground = true, name = "DefaultPreview")
    @Composable
    fun DefaultPreview() {
        CoroutineDemoTheme {
            Greeting("Android")
        }
    }

    @Preview(showBackground = true, widthDp = 320)
    @Composable
    fun MyApp(names: List<String> = listOf("World", "Compose")) {
        Column {
            for (name in names) {
                Greeting(name = name)
            }
        }
    }
}