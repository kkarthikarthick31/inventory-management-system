package com.example.demo.services.scheduledtask;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.models.Article;
import com.example.demo.models.Message;
import com.example.demo.repositories.ArticleRepository;
import com.example.demo.repositories.MessageRepository;

@Service
public class ScheduledTaskService implements IScheduledTaskService {

    private final ArticleRepository articleRepository;
    private final MessageRepository messageRepository;

    public ScheduledTaskService(
            ArticleRepository articleRepository,
            MessageRepository messageRepository) {

        this.articleRepository = articleRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    @Scheduled(cron = "0 0 8 * * *")
    public void checkDepletedStock() {

        System.out.println("Checking stock items...");

        List<Article> articles =
                articleRepository.findByQuantityLessThanEqual(10);

        System.out.println("Low stock articles found: " + articles.size());

        for (Article article : articles) {

            Message message = new Message();

            message.setRead(false);

            // If quantity is 0
            if (article.getQuantity() == 0) {

                message.setTitle(
                        "Out of Stock Alert: " + article.getName()
                );

                message.setContent(
                        "Item: " + article.getName() +
                        "\n\nThe item is currently OUT OF STOCK." +
                        "\n\nImmediate action required:" +
                        "\n1. Contact the supplier immediately." +
                        "\n2. Restock the item as soon as possible."
                );

            }

            // If quantity is between 1 and 10
            else {

                message.setTitle(
                        "Low Stock Alert: " + article.getName()
                );

                message.setContent(
                        "Item: " + article.getName() +
                        "\n\nCurrent quantity: " + article.getQuantity() +
                        "\n\nThe stock quantity is low. Please consider restocking this item." +
                        "\n\nRecommended actions:" +
                        "\n1. Notify the supplier for restocking." +
                        "\n2. Monitor the stock quantity regularly."
                );
            }

            messageRepository.save(message);

            System.out.println(
                    "Stock alert saved for: " + article.getName()
            );
        }
    }
}