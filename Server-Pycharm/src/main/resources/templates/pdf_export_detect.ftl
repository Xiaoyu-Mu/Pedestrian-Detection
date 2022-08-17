<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8"></meta>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"></meta>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"></meta>
    <title>PDF模板</title>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"></link>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"></link>
    <link rel="stylesheet" href="assets/css/style.min.css"></link>
    <style>
        body {
            font-family: SimSun;
        }
        #show-img-origin{
            width: 600px;
            height: 400px;
            margin-bottom: 40px;
        }
        #show-img-detect{
            width: 600px;
            height: 400px;
            margin-bottom: 60px;
        }
    </style>
</head>

<body>

<main class="main-wrapper">

    <div class="service-content-section section-inner-gap">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="service-content-wrapper">
                        <div class="content">
                            <h4 class="title-tag text-gradient">Detection result:</h4>
                            <h2 class="title">基 于 YOLOv3 的 物 体 检 测</h2>
                        </div>

                        <div class="image-group m-t-70  m-b-70">
                            <div class="image"><img id="show-img-origin" src="http://localhost:8080/file/${originImg}" alt=""></img></div>
                            <div class="image"><img id="show-img-detect" src="http://localhost:8080/file/${detectImg}" alt=""></img></div>
                        </div>

                        <div class="content">
                            <p>上采样：缩小图像（或称为下采样（subsampled），如池化）的主要目的有两个：1、使得图像符合显示区域的大小；
                                2、生成对应图像的缩略图。放大图像（或称为上采样（upSampling）或图像插值（interpolating））的主要目的是放大原图像,
                                从而可以显示在更高分辨率的显示设备上。
                                </p>

                            <p>先验框（anchor box）：就是帮助我们定好了常见目标的宽和高，在进行预测的时候，
                                我们可以利用这个已经定好的宽和高来进行处理，可以帮助我们进行预测，
                                作用就是辅助处理x_offset、y_offset、h和w。如下图所示，用的是coco数据集，输出是(13,13,(80+5)*3)，
                                乘3表示，有3个先验框，每个先验框都有85个参数，下图就有3个蓝色框，也即先验框，可以理解成给你的建议框，
                                识别的对象可能在这些建议框中，目的是带你得到更高的IOU，即更高置信度、更可能有对象得部分，
                                黄色框为真实最后显示的边界框，红色框表示中心位置。
                            </p>
                        </div>

                        <div class="content">
                            <p>置信度（confidence）：就是预测的先验框和真实框ground truth box（真实对象的框）的IOU值，
                                即先验框是否有对象的概率Pr(Object)，如进行人脸识别，一张图中有房子，树，车，人等，
                                识别时背景和人的身体都没有脸这个需要识别的对象，那么这些地方的置信度就是0，框中的人脸越多，置信度（有对象概率）就越大，置信度是检测中非常重要的参数
                            </p>

                            <p>1、yolov3提取多特征层进行目标检测，一共提取三个特征层，三个特征层位于主干特征提取网络darknet53的不同位置，分别位于中间层，中下层，底层，三个特征层的shape分别为(52,52,256)、(26,26,512)、(13,13,1024)，这三个特征层后面用于与上采样后的其他特征层堆叠拼接（Concat）</p>
                            <p>2、第三个特征层(13,13,1024)进行5次卷积处理（为了特征提取），处理完后一部分用于卷积+上采样UpSampling，另一部分用于输出对应的预测结果（13,13,75），Conv2D 3×3和Conv2D1×1两个卷积起通道调整的作用，调整成输出需要的大小。</p>
                            <p>3、卷积+上采样后得到(26,26,256)的特征层，然后与Darknet53网络中的特征层(26,26,512)进行拼接，得到的shape为(26,26,768)，再进行5次卷积，处理完后一部分用于卷积上采样，另一部分用于输出对应的预测结果(26,26,75)，Conv2D 3×3和Conv2D1×1同上为通道调整</p>
                            <p>4、之后再将3中卷积+上采样的特征层与shape为(52,52,256)的特征层拼接（Concat）,再进行卷积得到shape为(52,52,128)的特征层，最后再Conv2D 3×3和Conv2D1×1两个卷积，得到(52,52,75)特征层
                                最后图中有三个红框原因就是有些物体相对在图中较大，就用13×13检测，物体在图中比较小，就会归为52×52来检测</p>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</main>
</body>
</html>