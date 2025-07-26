import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:yaksok/route/app_router.dart';

class YaksokiApp extends ConsumerWidget {
  const YaksokiApp({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final router = ref.watch(appRouterProvider);

    return MaterialApp.router(
      routerConfig: router,
      title: '약속이',
      theme: ThemeData(
        useMaterial3: true,
        primarySwatch: Colors.teal,
      ),
    );
  }
}
