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
  DeviceEventEmitter
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

  _onChange(event) {
    console.log(event);
  }

  componentWillMount(){
    DeviceEventEmitter.addListener('onActivityStateChange', this._onChange)
  }

  componentDidMount(){
    console.log('mounted')
    Pollfish.initialize("e87bf486-712b-40ec-a6c0-d3ed6b5649a9", 'e87bf486-712b-40ec-a6c0-d3ed6b5649a9', 1)
    Pollfish.show();
  }

  componentWillUnmount() {
    DeviceEventEmitter.removeListener('onActivityStateChange', this._onChange);
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <TouchableOpacity onPress={()=>this.setState({modalVisible: true})}>
		<Text>Open Custom Component</Text>
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
