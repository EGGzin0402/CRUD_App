package com.example.crudapp.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crudapp.R
import com.example.crudapp.TopAppBar
import com.example.crudapp.data.Item
import com.example.crudapp.ui.AppViewModelProvider
import com.example.crudapp.ui.item.formatedPrice
import com.example.crudapp.ui.navigation.Destination
import com.himanshoe.charty.candle.CandleStickChart
import com.himanshoe.charty.candle.config.CandleStickConfig
import com.himanshoe.charty.candle.config.CandleStickDefaults
import com.himanshoe.charty.candle.model.CandleData
import com.himanshoe.charty.common.ComposeList
import com.himanshoe.charty.common.config.AxisConfig

object HomeDestination : Destination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()
    val candleDataList by viewModel.candleDataList.collectAsState()


    for (candleData in candleDataList) {
        Log.d("candleData", candleData.toString())
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            itemList = homeUiState.itemList,
            onItemClick = navigateToItemUpdate,
            modifier = modifier,
            contentPadding = innerPadding,
            candleDataList = candleDataList
        )
    }
}

@Composable
private fun HomeBody(
    itemList: List<Item>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    candleDataList: List<CandleData>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize(),
    ) {

        Row(
            modifier = modifier
                .height(LocalConfiguration.current.screenHeightDp.dp*0.7f)
        ) {

        if (itemList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            InventoryList(
                itemList = itemList,
                onItemClick = { onItemClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
        }

        Row(
            modifier = modifier
        ) {
            if (candleDataList.isEmpty()){
                Text(
                    text = "Nenhum dado disponível para exibir.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(contentPadding)
                )
            }else{

                Card(
                    modifier
                        .fillMaxWidth(),
                ){
                    Chart(
                        modifier,
                        ComposeList(candleDataList)
                    )
                }
            }
        }
    }
}

@Composable
private fun InventoryList(
    itemList: List<Item>,
    onItemClick: (Item) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.id }) { item ->
            InventoryItem(item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item) })
        }
    }
}

@Composable
private fun InventoryItem(
    item: Item, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))

                Spacer(Modifier.weight(1f))
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row {
                Text(
                    text = item.formatedPrice(),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = if (item.profit){"Entrada"} else{"Saída"},
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    candleData: ComposeList<CandleData>
) {

    CandleStickChart(
        modifier = modifier
            .fillMaxWidth()
            .height(270.dp),
        candleData = candleData,
        candleConfig = CandleStickConfig(
            positiveColor = Color(0xFF000000),
            negativeColor = Color(0xFFDADADA),
            wickColor = CandleStickDefaults.defaultCandleStickConfig().wickColor,
            canCandleScale = CandleStickDefaults.defaultCandleStickConfig().canCandleScale,
            wickWidthScale = CandleStickDefaults.defaultCandleStickConfig().wickWidthScale
        ),
        axisConfig = AxisConfig(
            showAxes = true,
            showGridLabel = true,
            showGridLines = true,
            axisColor = Color.Black,
            axisStroke = 10.0F,
            gridColor = Color.White,
            minLabelCount = 200,
        )
    )

}

@Preview
@Composable
private fun Card() {
    InventoryItem(Item(
        id = 0,
        name = "TESTE",
        price = 0.0,
        date = "04/02/2006",
        profit = true
    ))
}

@Preview(showBackground = true, heightDp = 640, widthDp = 480)
@Composable
private fun ChartPreview() {
    val sampleData = listOf(
        CandleData(0F, 0F, 3.0F, 4.0F),
        CandleData(0F, 0f, 4.0F, 3.5F),
        CandleData(0F, 0F, 3.0F, 4.0F),
        CandleData(0F, 0f, 4.0F, 3.5F),
        CandleData(0F, 0F, 3.0F, 4.0F),
        CandleData(0F, 0f, 4.0F, 3.5F),
        CandleData(0F, 0F, 3.0F, 4.0F),
        CandleData(0F, 0f, 4.0F, 3.5F),
    )
    Card{
        Chart(
            candleData = ComposeList(sampleData),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )
    }
}