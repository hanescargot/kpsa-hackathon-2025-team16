import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:yaksok/features/home/view/video_call_screen.dart';
import 'package:yaksok/util.dart';
import '../../../widgets/home_bottom_nav_bar.dart';
import '../../my_page/prescription_info.dart';
import 'package:go_router/go_router.dart';

class NewHomeScreen extends ConsumerStatefulWidget {
  const NewHomeScreen({super.key});

  @override
  ConsumerState<NewHomeScreen> createState() => _NewHomeScreenState();
}

class _NewHomeScreenState extends ConsumerState<NewHomeScreen> {
  final PageController _pageController = PageController();
  int _currentPage = 0;
  final List<String> _ads = [
    'assets/ad/ad1.png',
    'assets/ad/ad2.png',
    'assets/ad/ad3.png',
  ];
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    _timer = Timer.periodic(const Duration(seconds: 3), (Timer timer) {
      if (_currentPage < _ads.length - 1) {
        _currentPage++;
      } else {
        _currentPage = 0;
      }
      _pageController.animateToPage(
        _currentPage,
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeInOut,
      );
    });
  }

  @override
  void dispose() {
    _timer?.cancel();
    _pageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF9BC2FF),
      body: SafeArea(
        child: Stack(
          children: [
            Column(
              children: [
                // 광고 배너
                SizedBox(
                  height: 68,
                  child: PageView.builder(
                    controller: _pageController,
                    itemCount: _ads.length,
                    itemBuilder: (context, index) {
                      return Padding(
                        padding: const EdgeInsets.symmetric(horizontal: 16),
                        child: ClipRRect(
                          borderRadius: BorderRadius.circular(4),
                          child: Image.asset(_ads[index], fit: BoxFit.cover),
                        ),
                      );
                    },
                  ),
                ),
                Expanded(
                  child: SingleChildScrollView(
                    padding: const EdgeInsets.only(bottom: 80),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.stretch,
                      children: [
                        // 캐릭터 및 이름
                        Padding(
                          padding: const EdgeInsets.only(left: 16),
                          child: Row(
                            children: [
                              InkWell(
                                onTap: () => context.pushNamed(
                                  VideoNotificationScreen.routeName,
                                ),
                                child: Image.asset(
                                  'assets/logo/logo_rmbg.png',
                                  height: 80,
                                ),
                              ),
                              const SizedBox(width: 8),
                              RichText(
                                text: const TextSpan(
                                  style: TextStyle(
                                    fontSize: 20,
                                    fontFamily: 'MapleStory',
                                  ),
                                  children: [
                                    TextSpan(
                                      text: '최순복',
                                      style: TextStyle(
                                        fontWeight: FontWeight.bold,
                                        color: Colors.indigo,
                                      ),
                                    ),
                                    TextSpan(
                                      text: ' 님',
                                      style: TextStyle(color: Colors.white),
                                    ),
                                  ],
                                ),
                              ),
                            ],
                          ),
                        ),

                        RichText(
                          text: TextSpan(
                            style: const TextStyle(
                              fontSize: 16,
                              color: Colors.white,
                            ),
                            children: [
                              const TextSpan(
                                text: '     현재 최은복님의 순위는 ',
                                style: TextStyle(
                                  color: Colors.black87,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              TextSpan(
                                text: '76',
                                style: TextStyle(
                                  color: kColorPrimary,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              const TextSpan(
                                text: '위입니다.',
                                style: TextStyle(
                                  color: Colors.black87,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            ],
                          ),
                        ),

                        const SizedBox(height: 20),

                        // 복약 정보
                        Container(
                          margin: const EdgeInsets.symmetric(horizontal: 16),
                          padding: const EdgeInsets.all(16),
                          decoration: BoxDecoration(
                            color: Colors.white,
                            borderRadius: BorderRadius.circular(16),
                          ),
                          child: const Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                '오늘의 복약정보',
                                style: TextStyle(
                                  fontSize: 18,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                              SizedBox(height: 12),
                              Column(
                                children: [
                                  Row(
                                    children: [
                                      Column(
                                        children: [
                                          Icon(
                                            Icons
                                                .settings_system_daydream_rounded,
                                            color: Colors.blueAccent,
                                          ),

                                        ],
                                      ),
                                      SizedBox(width: 8,),
                                      Text(
                                        "아침 - ",
                                        style: TextStyle(
                                          fontFamily: 'MapleStory',
                                          color: Colors.black54,
                                        ),
                                      ),
                                      Text(
                                        "9:00",
                                        style: TextStyle(
                                          fontFamily: 'MapleStory',
                                          color: Colors.black54,
                                        ),
                                      ),
                                    ],
                                  ),
                                  Row(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      SizedBox(width: 8),
                                      Expanded(
                                        child: Wrap(
                                          spacing: 8,
                                          runSpacing: 8,
                                          children: [
                                            _PillChip('히트코나졸정'),
                                            _PillChip('피디정'),
                                            _PillChip('동구록시트로마이신'),
                                            _PillChip('모사피아정'),
                                          ],
                                        ),
                                      ),
                                    ],
                                  ),
                                ],
                              ),
                              SizedBox(height: 16),
                              Column(
                                children: [
                                  Row(
                                    children: [
                                      Column(
                                        children: [
                                          Icon(
                                            Icons.wb_sunny,
                                            color: Colors.orange,
                                          ),
                                        ],
                                      ),
                                      SizedBox(width: 8,),
                                      Text(
                                        "점심 - ",
                                        style: TextStyle(
                                          fontFamily: 'MapleStory',
                                          color: Colors.black54,
                                        ),
                                      ),
                                      Text(
                                        "13:00",
                                        style: TextStyle(
                                          fontFamily: 'MapleStory',
                                          color: Colors.black54,
                                        ),
                                      ),
                                    ],
                                  ),
                                  Row(
                                    crossAxisAlignment:
                                    CrossAxisAlignment.start,
                                    children: [
                                      SizedBox(width: 8),
                                      Expanded(
                                        child: Wrap(
                                          spacing: 8,
                                          runSpacing: 8,
                                          children: [
                                            _PillChip('히트코나졸정'),
                                            _PillChip('피디정'),
                                            _PillChip('동구록시트로마이신'),
                                            _PillChip('모사피아정'),
                                          ],
                                        ),
                                      ),
                                    ],
                                  ),
                                ],
                              ),

                              SizedBox(height: 16),
                              Column(
                                children: [
                                  Row(
                                    children: [
                                      Icon(
                                        Icons.nightlight_round,
                                        color: Colors.indigo,
                                      ),
                                      Text(
                                        "저녁 - ",
                                        style: TextStyle(
                                          fontFamily: 'MapleStory',
                                          color: Colors.black54,
                                        ),
                                      ),
                                      Text(
                                        "19:00",
                                        style: TextStyle(
                                          fontFamily: 'MapleStory',
                                          color: Colors.black54,
                                        ),
                                      ),
                                    ],
                                  ),
                                  Row(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Column(
                                        children: [

                                        ],
                                      ),
                                      SizedBox(width: 8),
                                      Expanded(
                                        child: Wrap(
                                          spacing: 8,
                                          runSpacing: 8,
                                          children: [
                                            _PillChip('모사피아정'),
                                            _PillChip('로이탄 연질캡슐'),
                                          ],
                                        ),
                                      ),
                                    ],
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ),

                        const SizedBox(height: 12),

                        // 처방약 정보
                        Container(
                          margin: const EdgeInsets.symmetric(horizontal: 16),
                          padding: const EdgeInsets.all(16),
                          decoration: BoxDecoration(
                            color: Colors.white,
                            borderRadius: BorderRadius.circular(16),
                          ),
                          child: const Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Row(
                                children: [
                                  Text(
                                    '처방약 정보',
                                    style: TextStyle(
                                      fontSize: 18,
                                      fontWeight: FontWeight.bold,
                                    ),
                                  ),
                                  SizedBox(width: 6),
                                  Icon(Icons.medication, color: Colors.pink),
                                ],
                              ),
                              SizedBox(height: 12),
                              PrescriptionInfoPage(),
                            ],
                          ),
                        ),

                        const SizedBox(height: 12),

                        // 날짜 정보
                        Padding(
                          padding: const EdgeInsets.symmetric(horizontal: 16),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                            children: const [
                              _DateBox(label: 'Start Date', date: '2025 7월 1일'),
                              SizedBox(width: 8),
                              _DateBox(label: 'End Date', date: '2025 7월 7일'),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
            Positioned(right: 0, left: 0, bottom: 0, child: HomeBottomNavBar()),
          ],
        ),
      ),
      // bottomNavigationBar: const HomeBottomNavBar(),
    );
  }
}

class _PillChip extends StatelessWidget {
  final String name;

  const _PillChip(this.name);

  @override
  Widget build(BuildContext context) {
    return Chip(
      label: Text(name),
      // backgroundColor: Colors.blue.shade50,
      backgroundColor: Colors.white,
      labelStyle: const TextStyle(color: Colors.black),
    );
  }
}

class _DateBox extends StatelessWidget {
  final String label;
  final String date;

  const _DateBox({required this.label, required this.date});

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 12, horizontal: 16),
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(16),
        ),
        child: Column(
          children: [
            const Icon(Icons.calendar_month, color: Colors.purple),
            const SizedBox(height: 4),
            Text(
              label,
              style: const TextStyle(fontSize: 12, color: Colors.black54),
            ),
            const SizedBox(height: 4),
            Text(date, style: const TextStyle(fontWeight: FontWeight.bold)),
          ],
        ),
      ),
    );
  }
}
