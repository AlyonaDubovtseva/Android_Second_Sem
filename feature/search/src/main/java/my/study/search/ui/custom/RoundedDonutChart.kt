package my.study.search.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

@Immutable
data class DonutChartConfig(
    val sectorCount: Int,
    val colors: List<Color>
)

@Composable
fun RoundedDonutChart(
    config: DonutChartConfig,
    modifier: Modifier = Modifier
) {
    require(config.sectorCount > 0) {
        "sectorCount must be greater than 0"
    }

    require(config.colors.size >= config.sectorCount) {
        "colors size must be greater than or equal to sectorCount"
    }

    require(config.colors.take(config.sectorCount).hasNoEqualNeighborColors()) {
        "Neighbor sector colors must be different"
    }

    var selectedSectorIndex by remember {
        mutableIntStateOf(NO_SELECTED_SECTOR)
    }

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .pointerInput(config) {
                detectTapGestures { tapOffset ->
                    selectedSectorIndex = detectSectorIndex(
                        tapOffset = tapOffset,
                        canvasSize = size.width.toFloat(),
                        sectorCount = config.sectorCount
                    )
                }
            }
    ) {
        val canvasSize = size.minDimension
        val strokeWidth = canvasSize * 0.22f
        val radius = canvasSize * 0.34f
        val circleRadius = strokeWidth / 2f

        val topLeft = Offset(
            x = center.x - radius,
            y = center.y - radius
        )

        val arcSize = Size(
            width = radius * 2,
            height = radius * 2
        )

        val sectorAngle = FULL_CIRCLE / config.sectorCount
        val gapAngle = 4f

        repeat(config.sectorCount) { index ->
            val startAngle = START_ANGLE + index * sectorAngle + gapAngle / 2f
            val sweepAngle = sectorAngle - gapAngle

            val baseColor = config.colors[index]
            val color = if (index == selectedSectorIndex) {
                baseColor.lighten()
            } else {
                baseColor
            }

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Butt
                )
            )
        }


        repeat(config.sectorCount) { index ->
            val startAngle = START_ANGLE + index * sectorAngle + gapAngle / 2f
            val sweepAngle = sectorAngle - gapAngle
            val endAngle = startAngle + sweepAngle

            val baseColor = config.colors[index]
            val color = if (index == selectedSectorIndex) {
                baseColor.lighten()
            } else {
                baseColor
            }

            val endPoint = pointOnCircle(
                center = center,
                radius = radius,
                angleDegrees = endAngle
            )

            drawCircle(
                color = color,
                radius = circleRadius,
                center = endPoint
            )
        }

        val textLayoutResult = textMeasurer.measure(
            text = config.sectorCount.toString(),
            style = TextStyle(
                color = Color.Black,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold
            )
        )

        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                x = center.x - textLayoutResult.size.width / 2f,
                y = center.y - textLayoutResult.size.height / 2f
            )
        )
    }
}

private fun detectSectorIndex(
    tapOffset: Offset,
    canvasSize: Float,
    sectorCount: Int
): Int {
    val center = Offset(
        x = canvasSize / 2f,
        y = canvasSize / 2f
    )

    val dx = tapOffset.x - center.x
    val dy = tapOffset.y - center.y

    val distance = hypot(dx, dy)

    val outerRadius = canvasSize * 0.45f
    val innerRadius = canvasSize * 0.22f

    if (distance !in innerRadius..outerRadius) {
        return NO_SELECTED_SECTOR
    }

    val angleFromThreeOClock = Math.toDegrees(
        atan2(dy.toDouble(), dx.toDouble())
    ).toFloat()

    val normalizedAngle = (angleFromThreeOClock - START_ANGLE + FULL_CIRCLE) % FULL_CIRCLE

    return (normalizedAngle / (FULL_CIRCLE / sectorCount)).toInt()
}

private fun pointOnCircle(
    center: Offset,
    radius: Float,
    angleDegrees: Float
): Offset {
    val angleRadians = angleDegrees * PI.toFloat() / 180f

    return Offset(
        x = center.x + radius * cos(angleRadians),
        y = center.y + radius * sin(angleRadians)
    )
}

private fun List<Color>.hasNoEqualNeighborColors(): Boolean {
    if (size <= 1) return true

    for (index in 0 until lastIndex) {
        if (this[index] == this[index + 1]) {
            return false
        }
    }

    return first() != last()
}

private fun Color.lighten(): Color {
    return Color(
        red = red + (1f - red) * 0.35f,
        green = green + (1f - green) * 0.35f,
        blue = blue + (1f - blue) * 0.35f,
        alpha = alpha
    )
}

private const val FULL_CIRCLE = 360f
private const val START_ANGLE = 180f
private const val NO_SELECTED_SECTOR = -1