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

let Pollfish = NativeModules.PollfishModule;

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

  componentWillMount(){}

  componentDidMount() {
    Pollfish.initialize("e87bf486-712b-40ec-a6c0-d3ed6b5649a9", 'e87bf486-712b-40ec-a6c0-d3ed6b5649a9', 4)
    DeviceEventEmitter.addListener('onPollfishEvent', this._onPollfishEvent)
    console.log('mounted')
  }

  componentWillUnmount() {
    DeviceEventEmitter.removeListener('onPollfishEvent', this._onPollfishEvent)
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <TouchableOpacity onPress={()=> {
          Pollfish.show();
          this.setState({modalVisible: true})}}>
		<Text>Open Custom Component</Text>
	</TouchableOpacity>
  <TouchableOpacity onPress={()=> {
    Pollfish.hide()}}>
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
