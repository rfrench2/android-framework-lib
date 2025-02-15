package info.anodsplace.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ButtonsPanel(
    actionText: String,
    onDismissRequest: () -> Unit,
    onAction: () -> Unit,
    leadingContent: @Composable (() -> Unit)? = null
) {
    Column {
        Divider()
        Row(
                Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            if (leadingContent != null) {
                leadingContent()
            }
            Spacer(modifier = Modifier.weight(1.0f).defaultMinSize(minWidth = 32.dp))
            Button(onClick = onDismissRequest, modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
            Button(onClick = onAction, modifier = Modifier.align(Alignment.CenterVertically).padding(start = 8.dp)) {
                Text(text = actionText)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}