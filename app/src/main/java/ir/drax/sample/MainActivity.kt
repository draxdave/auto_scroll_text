package ir.drax.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ir.drax.autoscrolltext.AutoScrollTextView

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AutoScrollTextView>(R.id.autoText).setText("my new Text my new Text my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text myText my new Text my new Text my new Text my new Text my new Text my new Text my new Text my new Text my new Text my new Text my new Text my new Text my new Text my new Text ")
    }
}