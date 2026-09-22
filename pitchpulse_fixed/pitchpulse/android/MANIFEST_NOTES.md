# AndroidManifest.xml changes needed

Add the internet permission (required for Retrofit calls) inside `<manifest>`, above `<application>`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

That's the only manifest change needed — no other permissions are required for
auth/settings/matches since we're not touching camera, storage, or location yet.
