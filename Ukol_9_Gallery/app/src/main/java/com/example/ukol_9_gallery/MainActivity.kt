package com.example.ukol_9_gallery

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.ukol_9_gallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->

        if (uri != null) {

            binding.ivSelectedImage.setImageURI(uri)
            Toast.makeText(this, "Obrázek načten!", Toast.LENGTH_SHORT).show()
        } else {

            Toast.makeText(this, "Výběr zrušen", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnPickImage.setOnClickListener {

            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // ... kód pro btnPickImage už máte ...

        // 3. Kliknutí na ODBARVIT
        binding.btnFilter.setOnClickListener {
            // Kontrola, jestli tam vůbec je obrázek
            if (binding.ivSelectedImage.drawable == null) {
                Toast.makeText(this, "Nejdřív vyber fotku!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // A) Získáme aktuální obrázek z ImageView jako Bitmapu
                // (Pozor: v praxi se to dělá bezpečněji, ale pro ukázku to stačí)
                val originalBitmap = (binding.ivSelectedImage.drawable as android.graphics.drawable.BitmapDrawable).bitmap

                // B) Převod na Černobílou
                val cernobilaBitmapa = vytvoritCernobilou(originalBitmap)

                // C) Vložíme upravenou fotku zpátky
                binding.ivSelectedImage.setImageBitmap(cernobilaBitmapa)
                Toast.makeText(this, "Filtr aplikován!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Toast.makeText(this, "Chyba při úpravě: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun vytvoritCernobilou(original: android.graphics.Bitmap): android.graphics.Bitmap {
        val width = original.width
        val height = original.height


        val cernobila = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888)


        val canvas = android.graphics.Canvas(cernobila)


        val paint = android.graphics.Paint()


        val colorMatrix = android.graphics.ColorMatrix()
        colorMatrix.setSaturation(0f)
        val filter = android.graphics.ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = filter


        canvas.drawBitmap(original, 0f, 0f, paint)

        return cernobila
    }
}

