# Menu host

Android sample app to explore the fairly new [`MenuHost`](https://developer.android.com/reference/androidx/core/view/MenuHost) API. It allows supplying menu items to add in the app bar menu, as well as controlling when to invalidate or remove them. This uses the [`MenuProvider`](https://developer.android.com/reference/androidx/core/view/MenuProvider) interface, which is lifecycle aware, meaning it will automatically clean itself up when the lifecycle attached to it is destroyed, or when it drops below a specified lifecycle state.

The sample also goes through the traditional ways of adding menu items from both an `Activity` and `Fragment`. For `Fragment`s, it showcases injecting menu items into its host `Activity`'s app bar menu, as well as the `Fragment`'s own app bar.

### Resources used in this sample
- [Working with the AppBar](https://developer.android.com/guide/fragments/appbar)
- [Fragments: The good (non-deprecated) parts](https://www.youtube.com/watch?v=OE-tDh3d1F4)
