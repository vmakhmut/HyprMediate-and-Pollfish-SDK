/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Modal,
  NativeModules,
  DeviceEventEmitter,
  AppState
} from 'react-native';
import SDKComponent from './SDKComponent'
//var HyprMediate = NativeModules.HyprMediateModule;

// let Pollfish = NativeModules.PollfishModule;
// let HyprMediate = NativeModules.HyprMediateModule;

export default class App extends Component<Props> {

  state= {
    modalVisible: false
  }

  closeModal = () => {
    this.setState({modalVisible: false})
  }

  _onPollfishEvent(event) {
    console.log(event);
  }

  _onHyperMediateEvent(event) {
    console.log(event);
  }

  componentWillMount() {
    DeviceEventEmitter.addListener('onPollfishEvent', this._onPollfishEvent)
    DeviceEventEmitter.addListener('onHyperMediateEvent', this._onHyperMediateEvent)
  }

  componentDidMount() {
    NativeModules.HMModule.addEvent("One", "Two", 3, function(o) {
        console.log('In Callback', o);
    });

    // Pollfish.initializeWithPosition("e87bf486-712b-40ec-a6c0-d3ed6b5649a9", 'e87bf486-712b-40ec-a6c0-d3ed6b5649a9',
    // false, Pollfish.SurveyFormat.BASIC, Pollfish.Position.TOP_LEFT)
    // Pollfish.initialize("e87bf486-712b-40ec-a6c0-d3ed6b5649a9", 'e87bf486-712b-40ec-a6c0-d3ed6b5649a9', false, Pollfish.SurveyFormat.THIRD_PARTY)
    // HyprMediate.initialize("dd8e761b-7650-4af7-9bc8-c018b5ac6d07", "e87bf486-712b-40ec-a6c0-d3ed6b5649a9")
    console.log('mounted')
  }

  componentWillUnmount() {
    DeviceEventEmitter.removeListener('onPollfishEvent', this._onPollfishEvent)
    DeviceEventEmitter.removeListener('onHyperMediateEvent', this._onHyperMediateEvent)
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <TouchableOpacity onPress={()=> {
          NativeModules.HMModule.show();
          // Pollfish.show();
          this.setState({modalVisible: true})}}>
		<Text>Open Custom Component</Text>
	</TouchableOpacity>
  <TouchableOpacity onPress={()=> {
    // HyprMediate.showAd()
  }}>
<Text>Hide Custom Component</Text>
</TouchableOpacity>
	{/*<Modal*/}
	  {/*animationType="fade"*/}
          {/*transparent={false}*/}
          {/*visible={this.state.modalVisible}>*/}
	{/*<SDKComponent closeComponent={this.closeModal}/>*/}
	{/*</Modal>*/}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
