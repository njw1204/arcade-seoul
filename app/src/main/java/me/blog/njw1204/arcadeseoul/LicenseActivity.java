package me.blog.njw1204.arcadeseoul;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        getSupportActionBar().setTitle("라이센스");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<SimpleRatingBar>\nMIT License\n" +
                                 "\n" +
                                 "Copyright (c) 2018 WillyYu\n" +
                                 "\n" +
                                 "Permission is hereby granted, free of charge, to any person obtaining a copy\n" +
                                 "of this software and associated documentation files (the \"Software\"), to deal\n" +
                                 "in the Software without restriction, including without limitation the rights\n" +
                                 "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n" +
                                 "copies of the Software, and to permit persons to whom the Software is\n" +
                                 "furnished to do so, subject to the following conditions:\n" +
                                 "\n" +
                                 "The above copyright notice and this permission notice shall be included in all\n" +
                                 "copies or substantial portions of the Software.\n" +
                                 "\n" +
                                 "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n" +
                                 "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n" +
                                 "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n" +
                                 "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n" +
                                 "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n" +
                                 "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE\n" +
                                 "SOFTWARE.\n\n");

        stringBuilder.append("<Retrofit>\nCopyright 2013 Square, Inc.\n" +
                                 "\n" +
                                 "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                 "you may not use this file except in compliance with the License.\n" +
                                 "You may obtain a copy of the License at\n" +
                                 "\n" +
                                 "http://www.apache.org/licenses/LICENSE-2.0\n" +
                                 "\n" +
                                 "Unless required by applicable law or agreed to in writing, software\n" +
                                 "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                 "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                 "See the License for the specific language governing permissions and\n" +
                                 "limitations under the License.\n\n");

        stringBuilder.append("<TinyDB>\nCopyright 2014 KC Ochibili\n" +
                                 "\n" +
                                 "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                                 "you may not use this file except in compliance with the License.\n" +
                                 "You may obtain a copy of the License at\n" +
                                 "\n" +
                                 "http://www.apache.org/licenses/LICENSE-2.0\n" +
                                 "\n" +
                                 "Unless required by applicable law or agreed to in writing, software\n" +
                                 "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                                 "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                                 "See the License for the specific language governing permissions and\n" +
                                 "limitations under the License.\n\n");

        stringBuilder.append("<이 프로그램에는 네이버에서 제공한 나눔글꼴이 포함되어 있습니다.>\n\n");

        stringBuilder.append("<이 프로그램에서 제공하는 오락실 정보, 오락실 사전은 https://namu.wiki 의 저작물이 일부 포함된 2차 저작물입니다." +
                                 " 해당 저작물에는 CC-BY-NC-SA 라이센스가 적용되어 있습니다.>\n\n");

        stringBuilder.append("<App Icon : Designed by Rawpixel.com>\n\n");

        stringBuilder.append("<Splash Image, Main Banner : Designed by Freepik>");

        EditText editText = findViewById(R.id.editText_license);
        editText.setText(stringBuilder.toString());
    }
}
