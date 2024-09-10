package com.devendraShendkar.musicPlayer

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.devendraShendkar.musicPlayer.databinding.ActivitySelectionBinding

class SelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectionBinding
    private lateinit var adapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = this.window
            window.statusBarColor = ContextCompat.getColor(this, R.color.fav)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        this.window.navigationBarColor = resources.getColor(R.color.fav)
        binding = ActivitySelectionBinding.inflate(layoutInflater)
        setTheme(MainActivity.currentTheme[MainActivity.themeIndex])
        setContentView(binding.root)
        binding.selectionRV.setItemViewCacheSize(30)
        binding.selectionRV.setHasFixedSize(true)
        binding.selectionRV.layoutManager = LinearLayoutManager(this)
        adapter = MusicAdapter(this, MainActivity.MusicListMA, selectionActivity = true)
        binding.selectionRV.adapter = adapter
        binding.backBtnSA.setOnClickListener { finish() }
        //for search View
        binding.searchViewSA.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean = true
            override fun onQueryTextChange(newText: String?): Boolean {
                MainActivity.musicListSearch = ArrayList()
                if(newText != null){
                    val userInput = newText.lowercase()
                    for (song in MainActivity.MusicListMA)
                        if(song.title.lowercase().contains(userInput))
                            MainActivity.musicListSearch.add(song)
                    MainActivity.search = true
                    adapter.updateMusicList(searchList = MainActivity.musicListSearch)
                }
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        //for black theme checking
        if(MainActivity.themeIndex == 4)
        {
            binding.searchViewSA.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
        }
    }
}