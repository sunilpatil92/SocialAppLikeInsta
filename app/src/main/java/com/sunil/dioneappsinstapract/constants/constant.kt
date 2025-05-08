package com.sunil.dioneappsinstapract.constants

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class constant {
    companion object{
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyRGF0YSI6eyJfaWQiOiI3IiwibmFtZSI6IlR1c2hhciBNIiwiY291bnRyeUNvZGUiOiIrOTEiLCJlbWFpbCI6IiIsInBob25lIjo5NjI0MzExOTQxLCJkZXNjcmlwdGlvbiI6Ik5vdGhpbmcgaXMgaW1wb3NzaWJsZSBpbiB0aGUgd29ybGQg8J-kl_CfpJfwn6SXIiwidGFncyI6IlNlbGVjdCB0YWciLCJwcm9maWxlIjoidXBsb2Fkcy9wcm9maWxlLzk4OUVFNjg3LTcwMEItNDFFNS05MEFFLTcyNUM2NDc3QkZCMC0xMjIwLTAwMDAwMDc4NERFRkUyQUUuanBlZyIsImlzVXNlclZlcmlmaWVkIjp0cnVlLCJpc0Jhbm5lZCI6ZmFsc2UsImRhdGVPZkJpcnRoIjoiMjAwNS0wMS0wMVQwMDowMDowMC4wMDBaIiwibGluayI6IiIsImlzRGVsZXRlZCI6ZmFsc2UsIm9sZFBob25lIjpudWxsLCJjcmVhdGVBdCI6IjIwMjQtMDMtMDlUMDg6MDg6MjcuOTMxWiIsInVwZGF0ZUF0IjoiMjAyNS0wMS0yOFQxMjozMzowNS43NTlaIiwiX192IjowLCJiaW9VcGRhdGUiOiIyMDI0LTEyLTAyVDEyOjA1OjAzLjk5OVoiLCJsYXN0T3RwU2VuZERhdGUiOiIyMDI1LTAyLTIyVDA5OjU4OjAyLjgyMVoiLCJvdHBTZW5kQ291bnQiOjIsInVzZXJUeXBlIjoiVXNlciIsImlzT3RwVmVyaWZpZWQiOnRydWV9LCJpYXQiOjE3NDAyMTgzNzJ9.lrG057xfn8L6ZqMPqVm1KfF6QM9lAix4bA_IYJiXtFo"

        val profileBase = "https://d5c5b4vepve5f.cloudfront.net/"

        val vdoBase = "https://cdn.tepnot.com/uploads/posts/input-video/"
        val vdoThumbBase = "https://d5c5b4vepve5f.cloudfront.net/uploads/posts/video/thumb/"
        val pastImgBase = "https://d5c5b4vepve5f.cloudfront.net/uploads/posts/image/"


        fun convertDateTime(s:String) : String{
            //val isoString = "2025-05-07T02:52:26.073Z"
            val instant = Instant.parse(s)
            val localDate = instant.atZone(ZoneId.of("UTC")).toLocalDate()
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            val formattedDate = formatter.format(localDate)

            return formattedDate
        }


        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        }

        inline fun ViewPager2.getCurrentViewHolder(): RecyclerView.ViewHolder? {
            val recyclerView = this.getChildAt(0) as? RecyclerView ?: return null
            return recyclerView.findViewHolderForAdapterPosition(this.currentItem)
        }

    }
}