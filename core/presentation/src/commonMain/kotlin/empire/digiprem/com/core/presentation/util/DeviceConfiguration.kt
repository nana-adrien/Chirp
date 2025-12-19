package empire.digiprem.com.core.presentation.util

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import kotlin.math.min


@Composable
fun currentDeviceConfigure(): DeviceConfiguration{
    val windowSizeClass= currentWindowAdaptiveInfo().windowSizeClass
    return DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
}


enum class DeviceConfiguration {
    MOBILE_PORTRAIT,
    MOBILE_LANDSCAPE,
    TABLET_PORTRAIT,
    TABLET_LANDSCAPE,
    DESKTOP;

    companion object {
        fun fromWindowSizeClass(windowSizeClass: WindowSizeClass): DeviceConfiguration {


            return with(windowSizeClass) {
                val smallestSideDp = min(minWidthDp, minHeightDp)
                val isTablet = smallestSideDp >= WIDTH_DP_MEDIUM_LOWER_BOUND
                val isLandscape = minWidthDp > minHeightDp
                when {
                    !isTablet && !isLandscape -> MOBILE_PORTRAIT
                    !isTablet && isLandscape -> MOBILE_LANDSCAPE
                    isTablet && !isLandscape -> TABLET_PORTRAIT
                    isTablet && isLandscape -> TABLET_LANDSCAPE

                    else -> DESKTOP
                }
                /*when {
// 1. Téléphone portrait
                    minWidthDp < WIDTH_DP_MEDIUM_LOWER_BOUND &&
                            minHeightDp >= HEIGHT_DP_MEDIUM_LOWER_BOUND ->
                        MOBILE_PORTRAIT

                    // 2. Téléphone landscape (DOIT être AVANT tablette)
                    minWidthDp >= WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp < HEIGHT_DP_MEDIUM_LOWER_BOUND ->
                        MOBILE_LANDSCAPE

                    // 3. Tablette portrait
                    minWidthDp in WIDTH_DP_MEDIUM_LOWER_BOUND until WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp >= HEIGHT_DP_EXPANDED_LOWER_BOUND ->
                        TABLE_PORTRAIT

                    // 4. Tablette landscape
                    minWidthDp >= WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp in HEIGHT_DP_MEDIUM_LOWER_BOUND until HEIGHT_DP_EXPANDED_LOWER_BOUND ->
                        TABLE_LANDSCAPE


                    *//*minWidthDp < WIDTH_DP_MEDIUM_LOWER_BOUND &&
                            minHeightDp >= HEIGHT_DP_MEDIUM_LOWER_BOUND -> MOBILE_PORTRAIT
                    minWidthDp >= WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp < HEIGHT_DP_MEDIUM_LOWER_BOUND -> MOBILE_LANDSCAPE

                    minWidthDp in WIDTH_DP_MEDIUM_LOWER_BOUND..WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp >= HEIGHT_DP_EXPANDED_LOWER_BOUND -> TABLE_PORTRAIT

                    minWidthDp >= WIDTH_DP_EXPANDED_LOWER_BOUND &&
                            minHeightDp in HEIGHT_DP_MEDIUM_LOWER_BOUND..HEIGHT_DP_EXPANDED_LOWER_BOUND -> TABLE_LANDSCAPE*//*

                    else -> DESKTOP
                }*/
            }

        }
    }
}