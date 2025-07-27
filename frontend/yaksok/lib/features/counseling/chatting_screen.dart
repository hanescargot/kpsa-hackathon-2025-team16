import 'package:flutter/material.dart';

class PharmacistChatScreen extends StatefulWidget {
  const PharmacistChatScreen({super.key});

  @override
  State<PharmacistChatScreen> createState() => _PharmacistChatScreenState();
}

class _PharmacistChatScreenState extends State<PharmacistChatScreen> {
  final TextEditingController _controller = TextEditingController();

  // 채팅 메시지 저장 리스트
  List<Map<String, dynamic>> messages = [
    {
      'from': 'user',
      'text': '안녕하세요.\n소화불량 약을 먹었는데\n여전히 배가 아파요',
    },
    {
      'from': 'pharmacist',
      'text': '아이고, 불편하셨겠어요.\n혹시 언제부터 아프셨고, 같이\n드신 약이나 음식이 있을까요?\n원인을 같이 한번 확인해볼게요',
    },
  ];

  void _sendMessage(String text) {
    if (text.trim().isEmpty) return;

    setState(() {
      messages.add({'from': 'user', 'text': text.trim()});
      _controller.clear();
    });

    Future.delayed(const Duration(milliseconds: 500), () {
      setState(() {
        messages.add({
          'from': 'pharmacist',
          'text': '조금 더 자세히 알려주시면\n도움이 될 수 있어요!',
        });
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFF9EC9FF),
      body: SafeArea(
        child: Column(
          children: [
            // 상단 타이틀
            Container(
              padding: const EdgeInsets.symmetric(vertical: 20),
              child: const Center(
                child: Text(
                  '복약 상담',
                  style: TextStyle(
                    fontSize: 20,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  ),
                ),
              ),
            ),

            const Divider(height: 1, color: Colors.blueAccent),

            // 채팅 영역
            Expanded(
              child: ListView.builder(
                padding: const EdgeInsets.all(16),
                itemCount: messages.length,
                itemBuilder: (context, index) {
                  final message = messages[index];
                  final isUser = message['from'] == 'user';

                  return Align(
                    alignment: isUser ? Alignment.centerRight : Alignment.centerLeft,
                    child: Container(
                      margin: const EdgeInsets.symmetric(vertical: 6),
                      padding: const EdgeInsets.all(12),
                      decoration: BoxDecoration(
                        color: isUser ? const Color(0xFFD2D8FF) : Colors.white,
                        borderRadius: BorderRadius.only(
                          topLeft: const Radius.circular(16),
                          topRight: const Radius.circular(16),
                          bottomLeft: Radius.circular(isUser ? 16 : 0),
                          bottomRight: Radius.circular(isUser ? 0 : 16),
                        ),
                      ),
                      child: Text(
                        message['text'],
                        style: const TextStyle(fontSize: 15),
                      ),
                    ),
                  );
                },
              ),
            ),

            // 입력창
            Container(
              padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 10),
              color: Colors.white,
              child: Row(
                children: [
                  Expanded(
                    child: TextField(
                      controller: _controller,
                      decoration: const InputDecoration(
                        hintText: '여기에 답변을 입력하세요.',
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(Radius.circular(24)),
                        ),
                        contentPadding: EdgeInsets.symmetric(horizontal: 16, vertical: 12),
                      ),
                    ),
                  ),
                  const SizedBox(width: 8),
                  IconButton(
                    onPressed: () => _sendMessage(_controller.text),
                    icon: const Icon(Icons.send, color: Colors.teal),
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
