package jp.ac.asojuku.st.myminislot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    var cross:Int = 0
    var btn1:String? = null
    var btn2:String? = null
    var btn3:String? = null
    var cnt:Int = 0
    var n:Int = 0
    var rand:Int = 0
    var checkArray:Array<Int> = arrayOf(0,0,0)
    var imageArray:Array<Int> = arrayOf(R.drawable.seven,R.drawable.bigwin,
            R.drawable.bar,R.drawable.banana,
            R.drawable.cherry,
            R.drawable.grape,R.drawable.lemon,
            R.drawable.orange,
            R.drawable.waltermelon)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        betcoin.text = intent.getIntExtra("BET_COIN",10).toString()
        havecoin.text = intent.getIntExtra("HAVE_COIN",1000).toString()
        backbutton.setOnClickListener{onStartButtonTapped(it)}
    }

    override fun onResume() {
        super.onResume()
        img1.setOnClickListener{onimgbuttonTapped(it,1)}
        img2.setOnClickListener{onimgbuttonTapped(it,2)}
        img3.setOnClickListener{onimgbuttonTapped(it,3)}
    }

    fun onStartButtonTapped(view: View?){
        val intent = Intent(this,MainActivity::class.java);
        intent.putExtra("BET_COIN",R.string.betcoin)
        intent.putExtra("HAVE_COIN",R.string.havecoin)
        this.startActivity(intent);
    }
    fun onimgbuttonTapped(view: View?,img:Int){
        rand = (Math.random() * imageArray.size).toInt()
        when(img){
            1 -> {
                if(btn1 != "OK"){
                    img1.setImageResource(imageArray[rand])
                    btn1 = "OK"
                    checkArray[cnt] = rand
                    cnt++
                    if(cnt == 3){check()}
                }
            }
            2 -> {
                if(btn2 != "OK"){
                    img2.setImageResource(imageArray[rand])
                    btn2 = "OK"
                    checkArray[cnt] = rand
                    cnt++
                    if(cnt == 3){check()}
                }
            }
            3 -> {
                if(btn3 != "OK"){
                    img3.setImageResource(imageArray[rand])
                    btn3 = "OK"
                    checkArray[cnt] = rand
                    cnt++
                    if(cnt == 3){check()}
                }
            }
        }
    }
    fun check(){
        if(checkArray[0] == checkArray[1] && checkArray[1] == checkArray[2]){
            when(checkArray[0]){
                0->cross = 20
                1->cross = 10
                2->cross = 5
                else->cross = 2
            }
        }else if(checkArray[0] == checkArray[1] || checkArray[0] == checkArray[2] || checkArray[1] == checkArray[2]){
            if(checkArray[0] == checkArray[1]){
                if(checkArray[0] == 0){
                    cross = 3
                }else{
                    cross = 1
                }
            }else if(checkArray[0] == checkArray[2]) {
                if(checkArray[0] == 0){
                    cross = 3
                }else{
                    cross = 1
                }
            }else{
                if(checkArray[1] == 0){
                    cross = 3
                }else{
                    cross = 1
                }
            }
        }else if(checkArray[0] == 7 && checkArray[1] == 4 && checkArray[2] == 6){
            cross = 30
        }else if(checkArray[0] == 8 && checkArray[1] == 3 && checkArray[2] == 5){
            cross = 10
        }
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        pref.edit().putInt("HAVE_COIN",cross * intent.getIntExtra("BET_COIN",10) + intent.getIntExtra("HAVE_COIN",10)).apply()
        havecoin.text = pref.getInt("HAVE_COIN",10).toString()
        pref.edit().putInt("BET_COIN",intent.getIntExtra("BET_COIN",10)).apply()
    }
}
