# ManifestAnalyzer
An Android application (Java) that retrieves the Android manifest of another application installed on the same device 


## How to use

1. All installed applications on the device will be listed in the spinner.You have to select one from the list as shown in Screenshots 1&2
2. After selecting an app from the list. CLick Manifest Analyzer button as shown in Screenshots 3.
3. A new activity will be open to show the content of Manifest file of the selected app, s shown in Screenshots 4.

### Screenshot 1 & 2
![Screenshot_1572951087](https://user-images.githubusercontent.com/17234785/68202203-48245100-ffd4-11e9-9688-082996540b96.png)  ![Screenshot_1572951100](https://user-images.githubusercontent.com/17234785/68202204-48245100-ffd4-11e9-8e6d-dfb4ebb64e8a.png)
### Screenshot 3 & 4                                                                   
![Screenshot_1572951103](https://user-images.githubusercontent.com/17234785/68202206-48245100-ffd4-11e9-8a59-206722f2424b.png)      ![Screenshot_1572951106](https://user-images.githubusercontent.com/17234785/68202207-48bce780-ffd4-11e9-8272-689b8904fe5f.png)

### Screenshot 5          
![Screenshot_1572975577](https://user-images.githubusercontent.com/17234785/68231669-850c3a00-000c-11ea-9c81-378833925214.png)

## Technical Review
 ### MVC Architecture
  The project was built with 3 differnt layers and one interface for methods structure:  
  
    1. Model :  presents set of object classes that will be used during the interaction between other layers
    2. View  :  presents activites and user-interfaces and handle user interactions.
    3. Controller  : access, retrieve and manage application data
    4. Interfaces  : presents the structure of all methods used by object to interact with outside world

![image](https://user-images.githubusercontent.com/17234785/68207039-254b6a00-ffdf-11e9-8c15-3690bf89833b.png)


 ### Model
  * This layer contains **Application** object class with 2 properties Name and Dir
  * Name: name of selected application
  * Dir: directory of the selected app apk file, this will be used to reach to Manifest file
  * This object will be used to fill the spinner with installed applications list
  
```java
public class Application {
    public String Name;
    public String Dir;

    public Application(String name,String dir) {
        Name = name;
        Dir = dir;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return Name;
    }
}
```

 ### View
 * This layer contains 2 Activity class MainActivity and infoActivity and will be used to handle the interaction with the user
 * MainActivity will used to make the user select an app and click the button to read the content of selected app Manifest file 
 * infoActivity will be used to show the content of the Manifest file content
 
 ### MainActivity.java

  * **populateInstalledAppSpinner** method will be called to fill the spinner with installed app list.
  * **OnClickManifestAnalyzer** method will be used to handle the click event of ManifestAnalyzer button and pass the result to infoActivity .

~~~ java
public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    AnalyzerController utility = new AnalyzerController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spInstalledApp);

        populateInstalledAppSpinner();
    }

    //ToDo: The method will be used to fill the spinner with all installed app on device
    public void populateInstalledAppSpinner() {

        //ToDo: Get all installed app list
        List<Application> spinnerArray = utility.getAllInstalledAppsList(this);

        //ToDo: Set adapter to the spinner
        final ArrayAdapter<Application> adapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_item, spinnerArray);
        spinner.setAdapter(adapter);
    }

    //ToDo: Click event for ManifestAnalyzer button
    public void OnClickManifestAnalyzer(View view) {
        //ToDo: Get selected app form the spinner
        Application application = (Application) spinner.getSelectedItem();

        //ToDo: Get selected app directory and read the content of the manifest file
        String manifestInfo =  utility.readManifestFileContent(application.Dir, getApplicationContext());

        //ToDo:  Send the content to info activity
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("ManifestContent", manifestInfo);
        startActivity(intent);
    }
}
~~~


 ### infoActivity.java

  * This activty will be used to show the content of selected Manifest file 
  * The content of Manifest file will be respresented as a json object

~~~java
public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView textView = findViewById(R.id.textInfo);

        //ToDo: Set a back button on the top of action bar to back to maim activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ToDo: Retrieve the content of manifest file from intent
        String ManifestContent = getIntent().getStringExtra("ManifestContent");

        //ToDo: Make the text view scrollable
        textView.setMovementMethod(new ScrollingMovementMethod());

        //ToDo: Set the content to text view
        textView.setText(ManifestContent);
    }
}
~~~

 ### Controller
  * This layer represents the busniess logic of the application 
  * All operation and function will be excuted here
  * **AnalyzerController**
   * Java class implements IAnalyzer methods that will be used to read the content of the manifest file
   * **getAllInstalledAppsList**  This method will be used to get a list of all installed apps.
   * **readManifestFileContent**  Read the manifest file of the selected app
   
~~~java 
public class AnalyzerController implements IAnalyzer {

    //TODO: This method will be used to get a list of all installed apps.
    @Override
    public List<Application> getAllInstalledAppsList(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<Application> applicationList = new ArrayList<>();
        applicationList.add(new Application("Please select an application", "TEST"));

        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            String appName = (String) packageManager.getApplicationLabel(packageInfo);
            applicationList.add(new Application(appName, packageInfo.packageName));
        }

        return applicationList;
    }

    //ToDo: Read the manifest file of the selected app
    @Override
    public String readManifestFileContent(String sourceDir, Context context) {
        StringBuilder result = new StringBuilder();
        Gson gson = new Gson();

        int flags = PackageManager.GET_ACTIVITIES
                | PackageManager.GET_CONFIGURATIONS
                | PackageManager.GET_DISABLED_COMPONENTS
                | PackageManager.GET_GIDS | PackageManager.GET_INSTRUMENTATION
                | PackageManager.GET_INTENT_FILTERS
                | PackageManager.GET_PERMISSIONS | PackageManager.GET_PROVIDERS
                | PackageManager.GET_RECEIVERS | PackageManager.GET_SERVICES
                | PackageManager.GET_SIGNATURES;
        try {
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(sourceDir, flags);
            return AppUtility.formatJsonObject(gson.toJson(pkgInfo));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return result.toString();
    }

} 
~~~~



 * **AppUtility** class contains  **formatJsonObject**  funcion will be used to reformat the json object of the manifest file content 

  ~~~java
  public class AppUtility {
    //TODO: This will reformat a json object.
    public static String formatJsonObject(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String formattedJsonObject = gson.toJson(json);

        return formattedJsonObject;
    }
}
  ~~~~

### IAnalyzer 
  * java interface contains all the methods that will be used in the project
  * **getAllInstalledAppsList** method to Get all installed application on the device
  * **readManifestFileContent** Read the manifest file of the selected app
  ~~~~java
  
public interface IAnalyzer {
     //ToDo: Get all installed application on the device
     List<Application> getAllInstalledAppsList(Context context);
     //ToDo: Read the manifest file of the selected app
     String readManifestFileContent(String sourceDir, Context context);
}
~~~~

### 3rd party library used
 * Gson A Java serialization/deserialization library to convert Java Objects into JSON and back
