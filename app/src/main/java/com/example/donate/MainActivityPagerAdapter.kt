import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

import com.example.donate.Fragments.ChatFragment
import com.example.donate.Fragments.PostFragment
import com.example.donate.R

@Suppress("DEPRECATION")
internal class MainActivityPagerAdapter(
    private val mContext: Context,
    mFragmentManager: FragmentManager
) :
    FragmentPagerAdapter(mFragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
               ChatFragment()
            }
            else -> {
              PostFragment()
            }
        }
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_chat, R.string.tab_post)
    }
}