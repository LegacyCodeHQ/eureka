export let graphData = `
{
  "nodes": [{
    "id": "SegmentedProgressBar progressBar",
    "group": 1
  }, {
    "id": "StorySlateView storySlate",
    "group": 1
  }, {
    "id": "TextView viewsAndReplies",
    "group": 1
  }, {
    "id": "StoriesSharedElementCrossFaderView storyCrossfader",
    "group": 1
  }, {
    "id": "ImageView blurContainer",
    "group": 1
  }, {
    "id": "FrameLayout storyCaptionContainer",
    "group": 1
  }, {
    "id": "FrameLayout storyContentContainer",
    "group": 1
  }, {
    "id": "Callback callback",
    "group": 1
  }, {
    "id": "List chrome",
    "group": 1
  }, {
    "id": "AnimatorSet animatorSet",
    "group": 1
  }, {
    "id": "Lazy viewModel$delegate",
    "group": 1
  }, {
    "id": "Lazy sharedViewModel$delegate",
    "group": 1
  }, {
    "id": "VideoControlsDelegate videoControlsDelegate",
    "group": 1
  }, {
    "id": "LifecycleDisposable lifecycleDisposable",
    "group": 1
  }, {
    "id": "LifecycleDisposable timeoutDisposable",
    "group": 1
  }, {
    "id": "long MAX_VIDEO_PLAYBACK_DURATION",
    "group": 1
  }, {
    "id": "long MIN_GIF_LOOPS",
    "group": 1
  }, {
    "id": "long MIN_GIF_PLAYBACK_DURATION",
    "group": 1
  }, {
    "id": "long MIN_TEXT_STORY_PLAYBACK",
    "group": 1
  }, {
    "id": "long CHARACTERS_PER_SECOND",
    "group": 1
  }, {
    "id": "long DEFAULT_DURATION",
    "group": 1
  }, {
    "id": "String ARG_STORY_RECIPIENT_ID",
    "group": 1
  }, {
    "id": "String ARG_STORY_ID",
    "group": 1
  }, {
    "id": "Companion Companion",
    "group": 1
  }, {
    "id": "StoryViewerPageViewModel getViewModel()",
    "group": 2
  }, {
    "id": "StoryViewerViewModel getSharedViewModel()",
    "group": 2
  }, {
    "id": "RecipientId getStoryRecipientId()",
    "group": 2
  }, {
    "id": "long getInitialStoryId()",
    "group": 2
  }, {
    "id": "void onViewCreated(View, Bundle)",
    "group": 2
  }, {
    "id": "void onResume()",
    "group": 2
  }, {
    "id": "void onPause()",
    "group": 2
  }, {
    "id": "void onDestroyView()",
    "group": 2
  }, {
    "id": "void onFinishForwardAction()",
    "group": 2
  }, {
    "id": "void onDismissForwardSheet()",
    "group": 2
  }, {
    "id": "long calculateDurationForText(TextContent)",
    "group": 2
  }, {
    "id": "float getVideoPlaybackPosition(PlayerState)",
    "group": 2
  }, {
    "id": "long getVideoPlaybackDuration(PlayerState)",
    "group": 2
  }, {
    "id": "void hideChromeImmediate()",
    "group": 2
  }, {
    "id": "void hideChrome()",
    "group": 2
  }, {
    "id": "void showChrome()",
    "group": 2
  }, {
    "id": "void animateChrome(float)",
    "group": 2
  }, {
    "id": "void adjustConstraintsForScreenDimensions(View, View, CardView)",
    "group": 2
  }, {
    "id": "void resumeProgress()",
    "group": 2
  }, {
    "id": "void pauseProgress()",
    "group": 2
  }, {
    "id": "void startReply()",
    "group": 2
  }, {
    "id": "void onStartDirectReply(long, RecipientId)",
    "group": 2
  }, {
    "id": "StartPage getViewsAndRepliesDialogStartPage()",
    "group": 2
  }, {
    "id": "void presentStory(StoryPost, int)",
    "group": 2
  }, {
    "id": "void presentSlate(StoryPost)",
    "group": 2
  }, {
    "id": "void onStateChanged(State, long)",
    "group": 2
  }, {
    "id": "void presentDistributionList(TextView, StoryPost)",
    "group": 2
  }, {
    "id": "void presentBlur(ImageView, StoryPost)",
    "group": 2
  }, {
    "id": "void presentCaption(TextView, TextView, View, StoryPost)",
    "group": 2
  }, {
    "id": "void onShowCaptionOverlay(TextView, TextView, View)",
    "group": 2
  }, {
    "id": "void onHideCaptionOverlay(TextView, TextView, View)",
    "group": 2
  }, {
    "id": "void presentFrom(TextView, StoryPost)",
    "group": 2
  }, {
    "id": "void presentDate(TextView, StoryPost)",
    "group": 2
  }, {
    "id": "void presentSenderAvatar(AvatarImageView, StoryPost)",
    "group": 2
  }, {
    "id": "void presentGroupAvatar(AvatarImageView, StoryPost)",
    "group": 2
  }, {
    "id": "void presentViewsAndReplies(StoryPost, ReplyState)",
    "group": 2
  }, {
    "id": "Fragment createFragmentForPost(StoryPost)",
    "group": 2
  }, {
    "id": "void setIsDisplayingLinkPreviewTooltip(boolean)",
    "group": 2
  }, {
    "id": "VideoControlsDelegate getVideoControlsDelegate()",
    "group": 2
  }, {
    "id": "void displayMoreContextMenu(View)",
    "group": 2
  }, {
    "id": "boolean singleTapOnMedia()",
    "group": 2
  }, {
    "id": "void onMediaReady()",
    "group": 2
  }, {
    "id": "void mediaNotAvailable()",
    "group": 2
  }, {
    "id": "void onReadyToAnimate()",
    "group": 2
  }, {
    "id": "void onAnimationStarted()",
    "group": 2
  }, {
    "id": "void onAnimationFinished()",
    "group": 2
  }, {
    "id": "void <init>()",
    "group": 2
  }, {
    "id": "void <clinit>()",
    "group": 2
  }, {
    "id": "boolean canSendMediaToStories()",
    "group": 2
  }, {
    "id": "Bundle requireArguments()",
    "group": 2
  }, {
    "id": "Context requireContext()",
    "group": 2
  }, {
    "id": "LifecycleOwner getViewLifecycleOwner()",
    "group": 2
  }, {
    "id": "FragmentManager getChildFragmentManager()",
    "group": 2
  }, {
    "id": "FragmentActivity requireActivity()",
    "group": 2
  }, {
    "id": "View requireView()",
    "group": 2
  }, {
    "id": "Context getContext()",
    "group": 2
  }, {
    "id": "Resources getResources()",
    "group": 2
  }, {
    "id": "void startActivity(Intent)",
    "group": 2
  }, {
    "id": "String getString(int, []Object)",
    "group": 2
  }, {
    "id": "String getString(int)",
    "group": 2
  }, {
    "id": "Fragment requireParentFragment()",
    "group": 2
  }],
  "links": [{
    "source": "StoryViewerPageViewModel getViewModel()",
    "target": "Lazy viewModel$delegate",
    "value": 1
  }, {
    "source": "StoryViewerViewModel getSharedViewModel()",
    "target": "Lazy sharedViewModel$delegate",
    "value": 1
  }, {
    "source": "RecipientId getStoryRecipientId()",
    "target": "Bundle requireArguments()",
    "value": 1
  }, {
    "source": "RecipientId getStoryRecipientId()",
    "target": "String ARG_STORY_RECIPIENT_ID",
    "value": 1
  }, {
    "source": "long getInitialStoryId()",
    "target": "Bundle requireArguments()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "Callback callback",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "ImageView blurContainer",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "FrameLayout storyContentContainer",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "FrameLayout storyCaptionContainer",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "StorySlateView storySlate",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "SegmentedProgressBar progressBar",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "TextView viewsAndReplies",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "StoriesSharedElementCrossFaderView storyCrossfader",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "StorySlateView storySlate",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "StoriesSharedElementCrossFaderView storyCrossfader",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "TextView viewsAndReplies",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "SegmentedProgressBar progressBar",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "List chrome",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "Context requireContext()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "LifecycleOwner getViewLifecycleOwner()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "LifecycleDisposable timeoutDisposable",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "LifecycleDisposable lifecycleDisposable",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "FragmentManager getChildFragmentManager()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "FragmentActivity requireActivity()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "FragmentManager getChildFragmentManager()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "View requireView()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "Context getContext()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "StoryViewerViewModel getSharedViewModel()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void adjustConstraintsForScreenDimensions(View, View, CardView)",
    "value": 1
  }, {
    "source": "void adjustConstraintsForScreenDimensions(View, View, CardView)",
    "target": "View requireView()",
    "value": 1
  }, {
    "source": "void adjustConstraintsForScreenDimensions(View, View, CardView)",
    "target": "Resources getResources()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "VideoControlsDelegate videoControlsDelegate",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "float getVideoPlaybackPosition(PlayerState)",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentSenderAvatar(AvatarImageView, StoryPost)",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentGroupAvatar(AvatarImageView, StoryPost)",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentCaption(TextView, TextView, View, StoryPost)",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "ImageView blurContainer",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentBlur(ImageView, StoryPost)",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "long DEFAULT_DURATION",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "Callback callback",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "FrameLayout storyCaptionContainer",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void startReply()",
    "value": 1
  }, {
    "source": "void startReply()",
    "target": "FragmentManager getChildFragmentManager()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void displayMoreContextMenu(View)",
    "value": 1
  }, {
    "source": "void displayMoreContextMenu(View)",
    "target": "Context requireContext()",
    "value": 1
  }, {
    "source": "void displayMoreContextMenu(View)",
    "target": "Context requireContext()",
    "value": 1
  }, {
    "source": "void displayMoreContextMenu(View)",
    "target": "FragmentManager getChildFragmentManager()",
    "value": 1
  }, {
    "source": "void displayMoreContextMenu(View)",
    "target": "void startActivity(Intent)",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "long getVideoPlaybackDuration(PlayerState)",
    "value": 1
  }, {
    "source": "long getVideoPlaybackDuration(PlayerState)",
    "target": "long MIN_GIF_LOOPS",
    "value": 1
  }, {
    "source": "long getVideoPlaybackDuration(PlayerState)",
    "target": "long MIN_GIF_PLAYBACK_DURATION",
    "value": 1
  }, {
    "source": "long getVideoPlaybackDuration(PlayerState)",
    "target": "long MAX_VIDEO_PLAYBACK_DURATION",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "RecipientId getStoryRecipientId()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void onReadyToAnimate()",
    "value": 1
  }, {
    "source": "void onReadyToAnimate()",
    "target": "StoryViewerViewModel getSharedViewModel()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentViewsAndReplies(StoryPost, ReplyState)",
    "value": 1
  }, {
    "source": "void presentViewsAndReplies(StoryPost, ReplyState)",
    "target": "TextView viewsAndReplies",
    "value": 1
  }, {
    "source": "void presentViewsAndReplies(StoryPost, ReplyState)",
    "target": "Resources getResources()",
    "value": 1
  }, {
    "source": "void presentViewsAndReplies(StoryPost, ReplyState)",
    "target": "String getString(int, []Object)",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentFrom(TextView, StoryPost)",
    "value": 1
  }, {
    "source": "void presentFrom(TextView, StoryPost)",
    "target": "String getString(int)",
    "value": 1
  }, {
    "source": "void presentFrom(TextView, StoryPost)",
    "target": "Context requireContext()",
    "value": 1
  }, {
    "source": "void presentFrom(TextView, StoryPost)",
    "target": "String getString(int, []Object)",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentDate(TextView, StoryPost)",
    "value": 1
  }, {
    "source": "void presentDate(TextView, StoryPost)",
    "target": "Context getContext()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentDistributionList(TextView, StoryPost)",
    "value": 1
  }, {
    "source": "void presentDistributionList(TextView, StoryPost)",
    "target": "Context requireContext()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "long calculateDurationForText(TextContent)",
    "value": 1
  }, {
    "source": "long calculateDurationForText(TextContent)",
    "target": "long CHARACTERS_PER_SECOND",
    "value": 1
  }, {
    "source": "long calculateDurationForText(TextContent)",
    "target": "long MIN_TEXT_STORY_PLAYBACK",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentStory(StoryPost, int)",
    "value": 1
  }, {
    "source": "void presentStory(StoryPost, int)",
    "target": "FragmentManager getChildFragmentManager()",
    "value": 1
  }, {
    "source": "void presentStory(StoryPost, int)",
    "target": "SegmentedProgressBar progressBar",
    "value": 1
  }, {
    "source": "void presentStory(StoryPost, int)",
    "target": "StorySlateView storySlate",
    "value": 1
  }, {
    "source": "void presentStory(StoryPost, int)",
    "target": "Fragment createFragmentForPost(StoryPost)",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void presentSlate(StoryPost)",
    "value": 1
  }, {
    "source": "void presentSlate(StoryPost)",
    "target": "StorySlateView storySlate",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void pauseProgress()",
    "value": 1
  }, {
    "source": "void pauseProgress()",
    "target": "SegmentedProgressBar progressBar",
    "value": 1
  }, {
    "source": "void pauseProgress()",
    "target": "VideoControlsDelegate videoControlsDelegate",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void resumeProgress()",
    "value": 1
  }, {
    "source": "void resumeProgress()",
    "target": "SegmentedProgressBar progressBar",
    "value": 1
  }, {
    "source": "void resumeProgress()",
    "target": "VideoControlsDelegate videoControlsDelegate",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void hideChromeImmediate()",
    "value": 1
  }, {
    "source": "void hideChromeImmediate()",
    "target": "AnimatorSet animatorSet",
    "value": 1
  }, {
    "source": "void hideChromeImmediate()",
    "target": "List chrome",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void onStartDirectReply(long, RecipientId)",
    "value": 1
  }, {
    "source": "void onStartDirectReply(long, RecipientId)",
    "target": "FragmentManager getChildFragmentManager()",
    "value": 1
  }, {
    "source": "void startReply()",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void displayMoreContextMenu(View)",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void displayMoreContextMenu(View)",
    "target": "LifecycleDisposable lifecycleDisposable",
    "value": 1
  }, {
    "source": "void displayMoreContextMenu(View)",
    "target": "Callback callback",
    "value": 1
  }, {
    "source": "void presentStory(StoryPost, int)",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void presentSlate(StoryPost)",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void resumeProgress()",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void hideChrome()",
    "value": 1
  }, {
    "source": "void hideChrome()",
    "target": "void animateChrome(float)",
    "value": 1
  }, {
    "source": "void animateChrome(float)",
    "target": "AnimatorSet animatorSet",
    "value": 1
  }, {
    "source": "void animateChrome(float)",
    "target": "List chrome",
    "value": 1
  }, {
    "source": "void animateChrome(float)",
    "target": "AnimatorSet animatorSet",
    "value": 1
  }, {
    "source": "void onViewCreated(View, Bundle)",
    "target": "void showChrome()",
    "value": 1
  }, {
    "source": "void showChrome()",
    "target": "void animateChrome(float)",
    "value": 1
  }, {
    "source": "void onStartDirectReply(long, RecipientId)",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void startReply()",
    "target": "StartPage getViewsAndRepliesDialogStartPage()",
    "value": 1
  }, {
    "source": "StartPage getViewsAndRepliesDialogStartPage()",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void displayMoreContextMenu(View)",
    "target": "RecipientId getStoryRecipientId()",
    "value": 1
  }, {
    "source": "void onResume()",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void onPause()",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void onDestroyView()",
    "target": "FragmentManager getChildFragmentManager()",
    "value": 1
  }, {
    "source": "void onDismissForwardSheet()",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void onStateChanged(State, long)",
    "target": "LifecycleDisposable timeoutDisposable",
    "value": 1
  }, {
    "source": "void onStateChanged(State, long)",
    "target": "TextView viewsAndReplies",
    "value": 1
  }, {
    "source": "void onStateChanged(State, long)",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void onStateChanged(State, long)",
    "target": "StorySlateView storySlate",
    "value": 1
  }, {
    "source": "void onShowCaptionOverlay(TextView, TextView, View)",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void onShowCaptionOverlay(TextView, TextView, View)",
    "target": "void onHideCaptionOverlay(TextView, TextView, View)",
    "value": 1
  }, {
    "source": "void onHideCaptionOverlay(TextView, TextView, View)",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void setIsDisplayingLinkPreviewTooltip(boolean)",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "VideoControlsDelegate getVideoControlsDelegate()",
    "target": "VideoControlsDelegate videoControlsDelegate",
    "value": 1
  }, {
    "source": "void onMediaReady()",
    "target": "StoryViewerViewModel getSharedViewModel()",
    "value": 1
  }, {
    "source": "void mediaNotAvailable()",
    "target": "StoryViewerViewModel getSharedViewModel()",
    "value": 1
  }, {
    "source": "void onAnimationStarted()",
    "target": "FrameLayout storyContentContainer",
    "value": 1
  }, {
    "source": "void onAnimationStarted()",
    "target": "ImageView blurContainer",
    "value": 1
  }, {
    "source": "void onAnimationStarted()",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void onAnimationFinished()",
    "target": "FrameLayout storyContentContainer",
    "value": 1
  }, {
    "source": "void onAnimationFinished()",
    "target": "ImageView blurContainer",
    "value": 1
  }, {
    "source": "void onAnimationFinished()",
    "target": "StoryViewerPageViewModel getViewModel()",
    "value": 1
  }, {
    "source": "void <init>()",
    "target": "Lazy viewModel$delegate",
    "value": 1
  }, {
    "source": "void <init>()",
    "target": "Lazy sharedViewModel$delegate",
    "value": 1
  }, {
    "source": "void <init>()",
    "target": "VideoControlsDelegate videoControlsDelegate",
    "value": 1
  }, {
    "source": "void <init>()",
    "target": "LifecycleDisposable lifecycleDisposable",
    "value": 1
  }, {
    "source": "void <init>()",
    "target": "LifecycleDisposable timeoutDisposable",
    "value": 1
  }, {
    "source": "void <init>()",
    "target": "Context requireContext()",
    "value": 1
  }, {
    "source": "void <init>()",
    "target": "Fragment requireParentFragment()",
    "value": 1
  }, {
    "source": "void <init>()",
    "target": "RecipientId getStoryRecipientId()",
    "value": 1
  }, {
    "source": "void <init>()",
    "target": "long getInitialStoryId()",
    "value": 1
  }, {
    "source": "void <clinit>()",
    "target": "Companion Companion",
    "value": 1
  }, {
    "source": "void <clinit>()",
    "target": "long MAX_VIDEO_PLAYBACK_DURATION",
    "value": 1
  }, {
    "source": "void <clinit>()",
    "target": "long MIN_GIF_LOOPS",
    "value": 1
  }, {
    "source": "void <clinit>()",
    "target": "long MIN_GIF_PLAYBACK_DURATION",
    "value": 1
  }, {
    "source": "void <clinit>()",
    "target": "long MIN_TEXT_STORY_PLAYBACK",
    "value": 1
  }, {
    "source": "void <clinit>()",
    "target": "long CHARACTERS_PER_SECOND",
    "value": 1
  }, {
    "source": "void <clinit>()",
    "target": "long DEFAULT_DURATION",
    "value": 1
  }],
  "meta": {
    "classInfo": {
      "name": "org.thoughtcrime.securesms.stories.viewer.page.StoryViewerPageFragment",
      "extends": "androidx.fragment.app.Fragment",
      "implements": ["org.thoughtcrime.securesms.mediapreview.MediaPreviewFragment$Events", "org.thoughtcrime.securesms.conversation.mutiselect.forward.MultiselectForwardBottomSheet$Callback", "org.thoughtcrime.securesms.stories.StorySlateView$Callback", "org.thoughtcrime.securesms.stories.viewer.text.StoryTextPostPreviewFragment$Callback", "org.thoughtcrime.securesms.stories.viewer.reply.StoriesSharedElementCrossFaderView$Callback"]
    }
  }
}
`;
