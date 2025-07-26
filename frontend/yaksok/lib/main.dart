import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:camera/camera.dart'; // ✅ 추가
import 'package:intl/date_symbol_data_local.dart';

import 'app.dart';

late List<CameraDescription> cameras;

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await initializeDateFormatting('ko_KR', null); // ✅ 캘린더 한국어 locale
  cameras = await availableCameras(); // ✅ 카메라 초기화
  runApp(const ProviderScope(child: YaksokiApp()));
}
