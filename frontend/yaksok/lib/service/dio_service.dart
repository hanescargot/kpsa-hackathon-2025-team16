// import 'package:dio/dio.dart';
//
// class DioService {
//   static final DioService _instance = DioService._internal();
//   factory DioService() => _instance;
//
//   late final Dio dio;
//
//   DioService._internal() {
//     dio = Dio(
//       BaseOptions(
//         baseUrl: 'http://3.35.14.212:8080', // 여기에 API 베이스 URL 입력
//         connectTimeout: const Duration(seconds: 10),
//         receiveTimeout: const Duration(seconds: 10),
//         headers: {
//           'Content-Type': 'application/json',
//         },
//       ),
//     );
//   }
// }
