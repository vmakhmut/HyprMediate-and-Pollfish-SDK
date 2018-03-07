

import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
TouchableOpacity
} from 'react-native';


export default class SDKComponent extends Component<Props> {
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          CUSTOM COMPONENT :A::A::A:A:A: 
        </Text>
         <TouchableOpacity onPress={this.props.closeComponent}>
		<Text>Back To Home</Text>
	</TouchableOpacity>
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
