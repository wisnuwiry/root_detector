import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:root_detector/root_detector.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _isRooted = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String isRooted;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      isRooted = await RootDetector.isRooted?.toString();
    } on PlatformException {
      isRooted = 'Failed to get root info in a device.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _isRooted = isRooted;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Root Detector'),
        ),
        body: Center(
          child: Text('Device is Rooted: $_isRooted\n'),
        ),
      ),
    );
  }
}
