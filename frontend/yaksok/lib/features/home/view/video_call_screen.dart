import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';
import 'package:audioplayers/audioplayers.dart';

class VideoNotificationScreen extends StatefulWidget {
  const VideoNotificationScreen({super.key});

  static const routeName = 'video-notification';

  @override
  State<VideoNotificationScreen> createState() =>
      _VideoNotificationScreenState();
}

class _VideoNotificationScreenState extends State<VideoNotificationScreen> {
  late final VideoPlayerController _controller;
  late final AudioPlayer _ringPlayer;

  bool _isRinging = true;
  bool _accepted = false;

  @override
  void initState() {
    super.initState();

    _controller =
        VideoPlayerController.asset('assets/video/video_call_sub_higher.mp4')
          ..initialize().then((_) {
            setState(() {});
          });

    _ringPlayer = AudioPlayer()..setReleaseMode(ReleaseMode.loop);
    _startRinging();
  }

  Future<void> _startRinging() async {
    await _ringPlayer.play(AssetSource('sounds/bell.mp3'));
  }

  Future<void> _stopRinging() async {
    await _ringPlayer.stop();
  }

  @override
  void dispose() {
    _controller.dispose();
    _ringPlayer.dispose();
    super.dispose();
  }

  Future<void> _accept() async {
    setState(() {
      _isRinging = false;
      _accepted = true;
    });
    await _stopRinging();
    await _controller.play();
  }

  Future<void> _decline() async {
    await _stopRinging();
    if (mounted) {
      Navigator.of(context).pop();
    }
  }

  void _onPressNumber(int n) {
    String msg;
    switch (n) {
      case 1:
        msg = '복용 완료로 처리했습니다.';
        break;
      case 2:
        msg = '잠시 뒤 다시 알림으로 처리했습니다.';
        break;
      case 3:
        msg = '오늘 복용 불가로 처리했습니다.';
        break;
      default:
        msg = '';
    }

    if (msg.isNotEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(msg)));
    }
    // TODO: 서버/로컬 저장 로직 추가
    Navigator.of(context).pop();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.black,
      body: SafeArea(
        child: _isRinging
            ? _buildIncomingCall()
            : _accepted
            ? _buildVideoCall()
            : const SizedBox.shrink(),
      ),
    );
  }

  Widget _buildIncomingCall() {
    return Column(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        const SizedBox(height: 32),
        Column(
          children: [
            SizedBox(
                width: 180,
                height: 180,
                child: Image.asset('assets/logo/logo_rmbg.png')),
            SizedBox(height: 40),
            Text('약속이', style: TextStyle(color: Colors.white, fontSize: 24)),
            SizedBox(height: 8),
            Text(
              '복약 여부를 확인하는 서비스 입니다.',
              style: TextStyle(color: Colors.white70, fontSize: 16),
            ),
          ],
        ),
        Padding(
          padding: const EdgeInsets.only(bottom: 48.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              _roundButton(
                color: Colors.red,
                icon: Icons.call_end,
                onTap: _decline,
              ),
              _roundButton(
                color: Colors.green,
                icon: Icons.call,
                onTap: _accept,
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildVideoCall() {
    return Stack(
      children: [
        // ✅ 세로 기준으로 화면 전체를 채우도록(Fill) — BoxFit.cover 패턴
        if (_controller.value.isInitialized)
          _fullScreenVideo()
        else
          const Center(child: CircularProgressIndicator(color: Colors.white)),

        // 하단 조작부
        Positioned(
          top: 280,
          left: 0,
          right: 0,
          bottom: 48,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              // 1,2,3 버튼 + 라벨
              Container(
                decoration: BoxDecoration(color: Colors.black54),
                padding: EdgeInsets.symmetric(vertical: 12),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: [
                    _numberButtonWithLabel(1, '복용 완료'),
                    _numberButtonWithLabel(2, '잠시 뒤 다시알림'),
                    _numberButtonWithLabel(3, '복용 불가능'),
                  ],
                ),
              ),
              // Spacer(),
              _roundButton(
                color: Colors.red,
                icon: Icons.call_end,
                onTap: _decline,
              ),
            ],
          ),
        ),
      ],
    );
  }

  /// 세로 길이 기준으로 화면을 꽉 채우기 위해 BoxFit.cover를 사용
  Widget _fullScreenVideo() {
    final size = _controller.value.size;

    return SizedBox.expand(
      child: FittedBox(
        fit: BoxFit.cover, // 화면을 가득 채우도록
        child: SizedBox(
          width: size.width,
          height: size.height,
          child: VideoPlayer(_controller),
        ),
      ),
    );
  }

  Widget _roundButton({
    required Color color,
    required IconData icon,
    required VoidCallback onTap,
  }) {
    return InkResponse(
      onTap: onTap,
      child: Container(
        width: 72,
        height: 72,
        decoration: BoxDecoration(color: color, shape: BoxShape.circle),
        child: Icon(icon, color: Colors.white, size: 32),
      ),
    );
  }

  /// 숫자 버튼 + 라벨
  Widget _numberButtonWithLabel(int n, String label) {
    return Column(
      children: [
        _numberButton(n),
        const SizedBox(height: 8),
        Text(
          label,
          style: const TextStyle(color: Colors.white, fontSize: 16, fontWeight: FontWeight.bold),
        ),
      ],
    );
  }

  Widget _numberButton(int n) {
    return InkResponse(
      onTap: () => _onPressNumber(n),
      child: CircleAvatar(
        radius: 28,
        backgroundColor: Colors.white70,
        child: Text(
          '$n',
          style: const TextStyle(
            color: Colors.black87,
            fontSize: 22,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    );
  }
}
