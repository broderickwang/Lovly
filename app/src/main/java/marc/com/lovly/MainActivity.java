package marc.com.lovly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
	LoveLayout loveLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loveLayout = (LoveLayout) findViewById(R.id.lovely);
	}

	public void addLove(View v){
		loveLayout.addLove();
	}
}
