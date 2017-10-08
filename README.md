# react-native-android-media-path

# Description
React Native Android component to get real media path. You can cobine with camera roll, if you need file path using uri from camera roll.

# Installation
This component does't have npm install, so you must install manually.
```
  npm install --save https://github.com/RZulfikri/react-native-android-media-path.git
```
then user 
```
  react-native link react-native-android-media-path
```

# Usage
```
  ...
  import GetRealPath from 'react-native-android-media-path'
  ...
  
  export default class extend Components{
    ...
    componentWillMount() {
        try {
        const videos = await CameraRoll.getPhotos({first: 10, assetType: 'Videos'})
        const videoFix = await videos.edges.filter(item => { return item.node.image.height > 0 && item.node.image.width > 0 })
        const videoFixPath = await Promise.all(videoFix.map(async item => {
          return {
            node: {
              ...item.node,
              image: {
                ...item.node.image,
                path: `file://${await GetRealPath.getRealPathFromURI(item.node.image.uri)}`
              }
            }
          }
        }))
        console.tron.warn(videoFixPath)
      } catch (error) {
        console.tron.warn(error)
      }
    }
    ...
  }
```
