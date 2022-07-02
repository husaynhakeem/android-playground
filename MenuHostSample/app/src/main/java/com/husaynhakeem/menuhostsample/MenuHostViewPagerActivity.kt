package com.husaynhakeem.menuhostsample

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MenuHostViewPagerActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_host_view_pager)

        viewPager = findViewById(R.id.menuHostViewPager)
        viewPager.adapter = object : FragmentStateAdapter(this) {

            override fun createFragment(position: Int) =
                MenuHostViewPagerFragment.newInstance(position)

            override fun getItemCount() = 3
        }
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}

/**
 * A [Fragment] that sets up its menu using its host [Activity] as a [MenuHost]. Since instances of
 * this [Fragment] are used in a [ViewPager2], the menu items it adds are removed when the [Fragment]
 * is no longer in the resumed state.
 */
class MenuHostViewPagerFragment : Fragment(R.layout.fragment_menu_host_view_pager) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt(KEY_ID) ?: 0

        // Set up menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_view_pager, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.viewPagerItemMenu -> {
                        val message = getString(R.string.view_pager_menu_item_click_message, id)
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }

            override fun onPrepareMenu(menu: Menu) {
                val item = menu.findItem(R.id.viewPagerItemMenu)
                item.title = id.toString()
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Set up UI
        val textView = view.findViewById<TextView>(R.id.menuHostTextView)
        textView.text = id.toString()
    }

    companion object {
        private const val KEY_ID = "key-id"

        fun newInstance(id: Int): MenuHostViewPagerFragment {
            return MenuHostViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_ID, id)
                }
            }
        }
    }
}