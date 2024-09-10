package com.devendraShendkar.musicPlayer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.devendraShendkar.musicPlayer.databinding.ActivityFavouriteBinding


class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private lateinit var adapter: FavouriteAdapter

    companion object{
        var favouriteSongs: ArrayList<Music> = ArrayList()
        var favouritesChanged: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = this.window
            window.statusBarColor = ContextCompat.getColor(this, R.color.fav)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        this.window.navigationBarColor = resources.getColor(R.color.fav)
        setTheme(MainActivity.currentTheme[MainActivity.themeIndex])
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        favouriteSongs = checkPlaylist(favouriteSongs)
        binding.backBtnFA.setOnClickListener { finish() }
        binding.favouriteRV.setHasFixedSize(true)
        binding.favouriteRV.setItemViewCacheSize(13)
        binding.favouriteRV.layoutManager = GridLayoutManager(this,3)
        adapter = FavouriteAdapter(this, favouriteSongs)
        binding.favouriteRV.adapter = adapter

        favouritesChanged = false

        if(favouriteSongs.size < 1) binding.shuffleBtnFA.visibility = View.INVISIBLE

        if(favouriteSongs.isNotEmpty()) binding.instructionFV.visibility = View.GONE

        binding.shuffleBtnFA.setOnClickListener {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("index", 0)
            intent.putExtra("class", "FavouriteShuffle")
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if(favouritesChanged) {
            adapter.updateFavourites(favouriteSongs)
            favouritesChanged = false
        }
    }
}